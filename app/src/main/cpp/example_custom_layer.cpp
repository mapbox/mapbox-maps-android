#include <GLES2/gl2.h>
#include <android/log.h>
#include <jni.h>
#include <sstream>

// DEBUGGING

const char* LOG_TAG = "Custom Layer Cpp Example";

#define LOG(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

const char* stringFromError(GLenum err) {
    switch (err) {
        case GL_INVALID_ENUM:
            return "GL_INVALID_ENUM";

        case GL_INVALID_VALUE:
            return "GL_INVALID_VALUE";

        case GL_INVALID_OPERATION:
            return "GL_INVALID_OPERATION";

        case GL_INVALID_FRAMEBUFFER_OPERATION:
            return "GL_INVALID_FRAMEBUFFER_OPERATION";

        case GL_OUT_OF_MEMORY:
            return "GL_OUT_OF_MEMORY";

#ifdef GL_TABLE_TOO_LARGE
        case GL_TABLE_TOO_LARGE:
        return "GL_TABLE_TOO_LARGE";
#endif

#ifdef GL_STACK_OVERFLOW
        case GL_STACK_OVERFLOW:
        return "GL_STACK_OVERFLOW";
#endif

#ifdef GL_STACK_UNDERFLOW
        case GL_STACK_UNDERFLOW:
        return "GL_STACK_UNDERFLOW";
#endif

#ifdef GL_CONTEXT_LOST
        case GL_CONTEXT_LOST:
        return "GL_CONTEXT_LOST";
#endif

        default:
            return "GL_UNKNOWN";
    }
}

struct Error : std::runtime_error {
    using std::runtime_error::runtime_error;
};

void checkError(const char *cmd, const char *file, int line) {

    GLenum err = GL_NO_ERROR;
    if ((err = glGetError()) != GL_NO_ERROR) {
        std::ostringstream message;
        message << cmd  << ": Error " << stringFromError(err);

        // Check for further errors
        while ((err = glGetError()) != GL_NO_ERROR) {
            message << ", " << stringFromError(err);
        }

        message << " at " << file << ":" << line;
        __android_log_write(ANDROID_LOG_ERROR, LOG_TAG, message.str().c_str());
// TODO: getError is not working properly, needs to be fixed in gl-native.
// https://github.com/mapbox/mapbox-maps-android/issues/860
//        throw Error(message.str());
    }
}

#ifndef NDEBUG
#define GL_CHECK_ERROR(cmd) ([&]() { struct __MBGL_C_E { ~__MBGL_C_E() noexcept(false) { checkError(#cmd, __FILE__, __LINE__); } } __MBGL_C_E; return cmd; }())
#else
#define GL_CHECK_ERROR(cmd) (cmd)
#endif

void checkLinkStatus(GLuint program) {
    GLint isLinked = 0;
    glGetProgramiv(program, GL_LINK_STATUS, &isLinked);
    if (isLinked == GL_FALSE) {
        GLint maxLength = 0;
        glGetProgramiv(program, GL_INFO_LOG_LENGTH, &maxLength);
        GLchar infoLog[maxLength];
        glGetProgramInfoLog(program, maxLength, &maxLength, &infoLog[0]);
        __android_log_write(ANDROID_LOG_ERROR, LOG_TAG, &infoLog[0]);
        throw Error(infoLog);
    }

}

void checkCompileStatus(GLuint shader) {
    GLint isCompiled = 0;
    glGetShaderiv(shader, GL_COMPILE_STATUS, &isCompiled);
    if (isCompiled == GL_FALSE) {
        GLint maxLength = 0;
        glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &maxLength);

        // The maxLength includes the NULL character
        GLchar errorLog[maxLength];
        glGetShaderInfoLog(shader, maxLength, &maxLength, &errorLog[0]);
        __android_log_write(ANDROID_LOG_ERROR, LOG_TAG, &errorLog[0]);
        throw Error(errorLog);
    }
}

// /DEBUGGING

static const GLchar * vertexShaderSource = "attribute vec2 a_pos; void main() { gl_Position = vec4(a_pos, 0, 1); }";
static const GLchar * fragmentShaderSource = "uniform highp vec4 fill_color; void main() { gl_FragColor = fill_color; }";

GLuint program = 0;
GLuint vertexShader = 0;
GLuint fragmentShader = 0;
GLuint buffer = 0;
GLuint a_pos = 0;
GLuint fill_color = 0;

static GLfloat color[]= { 0.0f, 1.0f, 0.0f, 1.0f };

// CustomLayerRenderParameters class/method lookups, cached once in JNI_OnLoad so nativeRender
// (called every frame) doesn't repeat them. paramsClass is a global ref since the local ref
// FindClass returns would not outlive JNI_OnLoad. Looked up once for the process lifetime
// (not per-instance in nativeInitialize/nativeDeinitialize) since multiple custom-layer
// instances can be initialized/deinitialized independently.
jclass paramsClass = nullptr;
jmethodID getWidthMethod = nullptr;
jmethodID getHeightMethod = nullptr;
jmethodID getLatitudeMethod = nullptr;
jmethodID getLongitudeMethod = nullptr;
jmethodID getZoomMethod = nullptr;
jmethodID getBearingMethod = nullptr;
jmethodID getPitchMethod = nullptr;
jmethodID getFieldOfViewMethod = nullptr;
jmethodID getProjectionMatrixArrayMethod = nullptr;

void JNICALL nativeInitialize(JNIEnv*, jobject) {
    LOG("nativeInitialize");

    // Debug info
    int maxAttrib;
    GL_CHECK_ERROR(glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, &maxAttrib));
    LOG("Max vertex attributes: %i", maxAttrib);

    program = GL_CHECK_ERROR(glCreateProgram());
    vertexShader = GL_CHECK_ERROR(glCreateShader(GL_VERTEX_SHADER));
    fragmentShader = GL_CHECK_ERROR(glCreateShader(GL_FRAGMENT_SHADER));

    GL_CHECK_ERROR(glShaderSource(vertexShader, 1, &vertexShaderSource, nullptr));
    GL_CHECK_ERROR(glCompileShader(vertexShader));
    checkCompileStatus(vertexShader);
    GL_CHECK_ERROR(glAttachShader(program, vertexShader));
    GL_CHECK_ERROR(glShaderSource(fragmentShader, 1, &fragmentShaderSource, nullptr));
    GL_CHECK_ERROR(glCompileShader(fragmentShader));
    checkCompileStatus(fragmentShader);
    GL_CHECK_ERROR(glAttachShader(program, fragmentShader));
    GL_CHECK_ERROR(glLinkProgram(program));
    checkLinkStatus(program);

    a_pos = GL_CHECK_ERROR(glGetAttribLocation(program, "a_pos"));
    fill_color = GL_CHECK_ERROR(glGetUniformLocation(program, "fill_color"));

    GLfloat background[] = { -1, -1, 1, -1, -1, 1, 1, 1 };
    GL_CHECK_ERROR(glGenBuffers(1, &buffer));
    GL_CHECK_ERROR(glBindBuffer(GL_ARRAY_BUFFER, buffer));
    GL_CHECK_ERROR(glBufferData(GL_ARRAY_BUFFER, 8 * sizeof(GLfloat), background, GL_STATIC_DRAW));
}

void JNICALL nativeRender(JNIEnv* env, jobject, jobject parameters) {
    LOG("nativeRender");

    // Read CustomLayerRenderParameters via the method IDs cached in JNI_OnLoad --
    // looking them up again here would repeat that work every frame.
    double width = env->CallDoubleMethod(parameters, getWidthMethod);
    double height = env->CallDoubleMethod(parameters, getHeightMethod);
    double latitude = env->CallDoubleMethod(parameters, getLatitudeMethod);
    double longitude = env->CallDoubleMethod(parameters, getLongitudeMethod);
    double zoom = env->CallDoubleMethod(parameters, getZoomMethod);
    double bearing = env->CallDoubleMethod(parameters, getBearingMethod);
    double pitch = env->CallDoubleMethod(parameters, getPitchMethod);
    double fieldOfView = env->CallDoubleMethod(parameters, getFieldOfViewMethod);
    auto projectionMatrix = static_cast<jdoubleArray>(env->CallObjectMethod(parameters, getProjectionMatrixArrayMethod));

    LOG("width: %f, height: %f ", width, height);
    LOG("lat: %f, lng: %f ", latitude, longitude);
    LOG("zoom: %f, bearing: %f, pitch: %f, filedOfView: %f", zoom, bearing, pitch, fieldOfView);

    // Parse out projectionMatrix from CustomLayerRenderParameters.
    int size = env->GetArrayLength(projectionMatrix);
    jdouble* projectionMatrixArray = env->GetDoubleArrayElements(projectionMatrix, nullptr);
    for (int i = 0; i < size; i++) {
        LOG("projectionMatrix[%d]: %f", i, projectionMatrixArray[i]);
    }
    env->ReleaseDoubleArrayElements(projectionMatrix, projectionMatrixArray, JNI_ABORT);

    // Open GL code starts here
    glUseProgram(program);
    glBindBuffer(GL_ARRAY_BUFFER, buffer);
    glEnableVertexAttribArray(a_pos);
    glVertexAttribPointer(a_pos, 2, GL_FLOAT, GL_FALSE, 0, NULL);
    glDisable(GL_STENCIL_TEST);
    glDisable(GL_DEPTH_TEST);
    glUniform4fv(fill_color, 1, color);

    GL_CHECK_ERROR(glUseProgram(program));
    GL_CHECK_ERROR(glBindBuffer(GL_ARRAY_BUFFER, buffer));
    GL_CHECK_ERROR(glEnableVertexAttribArray(a_pos));
    GL_CHECK_ERROR(glVertexAttribPointer(a_pos, 2, GL_FLOAT, GL_FALSE, 0, NULL));
    GL_CHECK_ERROR(glDisable(GL_STENCIL_TEST));
    GL_CHECK_ERROR(glDisable(GL_DEPTH_TEST));
    GL_CHECK_ERROR(glUniform4fv(fill_color, 1, color));
    GL_CHECK_ERROR(glDrawArrays(GL_TRIANGLE_STRIP, 0, 4));
}

void JNICALL nativeContextLost(JNIEnv*, jobject) {
    LOG("nativeContextLost");
    program = 0;
}

void JNICALL nativeDeinitialize(JNIEnv*, jobject, jfloat red, jfloat green, jfloat blue, jfloat alpha) {
    LOG("nativeDeinitialize");
    if (program) {
        glDeleteBuffers(1, &buffer);
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteProgram(program);
    }
}

void JNICALL nativeSetColor(JNIEnv*, jobject, jfloat red, jfloat green, jfloat blue, jfloat alpha) {
    LOG("nativeSetColor: %.2f, %.2f, %.2f, %.2f", red, green, blue, alpha);
    color[0] = red;
    color[1] = green;
    color[2] = blue;
    color[3] = alpha;
}

extern "C" JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *) {
    __android_log_write(ANDROID_LOG_INFO, LOG_TAG, "OnLoad");

    JNIEnv *env = nullptr;
    vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6);

    jclass customLayerClass = env->FindClass("com/mapbox/maps/testapp/examples/customlayer/NativeExampleCustomLayer");

    // A class global ref and its method IDs stay valid for the process lifetime, so there's
    // nothing to tear down; looking these up here (rather than per-instance in
    // nativeInitialize/nativeDeinitialize) avoids one instance's teardown invalidating the
    // cache while another instance is still rendering.
    jclass localParamsCls = env->FindClass("com/mapbox/maps/CustomLayerRenderParameters");
    paramsClass = static_cast<jclass>(env->NewGlobalRef(localParamsCls));
    env->DeleteLocalRef(localParamsCls);
    getWidthMethod = env->GetMethodID(paramsClass, "getWidth", "()D");
    getHeightMethod = env->GetMethodID(paramsClass, "getHeight", "()D");
    getLatitudeMethod = env->GetMethodID(paramsClass, "getLatitude", "()D");
    getLongitudeMethod = env->GetMethodID(paramsClass, "getLongitude", "()D");
    getZoomMethod = env->GetMethodID(paramsClass, "getZoom", "()D");
    getBearingMethod = env->GetMethodID(paramsClass, "getBearing", "()D");
    getPitchMethod = env->GetMethodID(paramsClass, "getPitch", "()D");
    getFieldOfViewMethod = env->GetMethodID(paramsClass, "getFieldOfView", "()D");
    getProjectionMatrixArrayMethod = env->GetMethodID(paramsClass, "getProjectionMatrixArray", "()[D");

    JNINativeMethod methods[] = {
        {"initialize", "()V", reinterpret_cast<void *>(&nativeInitialize)},
        {"render", "(Lcom/mapbox/maps/CustomLayerRenderParameters;)V", reinterpret_cast<void *>(&nativeRender)},
        {"contextLost", "()V", reinterpret_cast<void *>(&nativeContextLost)},
        {"deinitialize", "()V", reinterpret_cast<void *>(&nativeDeinitialize)},
        {"setColor", "(FFFF)V", reinterpret_cast<void *>(&nativeSetColor)}
    };

    if (env->RegisterNatives(customLayerClass, methods, sizeof(methods)/sizeof(JNINativeMethod)) < 0) {
        env->ExceptionDescribe();
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *, void *) {
}