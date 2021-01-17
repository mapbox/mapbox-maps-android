'use strict';

const fs = require('fs');
const ejs = require('ejs');

global.iff = function (condition, val) {
  return condition() ? val : "";
}

global.camelize = function (str) {
  return str.replace(/(?:^|-)(.)/g, function (_, x) {
    return x.toUpperCase();
  });
}

const excludeClasses = JSON.parse(fs.readFileSync('scripts/sanity-test/exclude-sanity-test-gen.json', 'utf8'));
const appBasePath = 'app/src/main/java/com/mapbox/maps/testapp';
const testBasePath = 'app/src/androidTest/java/com/mapbox/maps/testapp/activity';
const subPackages = fs.readdirSync(appBasePath);
const ejsConversionTask = ejs.compile(fs.readFileSync('scripts/sanity-test/template-sanity-test.ejs', 'utf8'), {strict: true});

if (!fs.existsSync(testBasePath)){
  fs.mkdirSync(testBasePath);
}

console.log("\nGenerating test activities:\n");
var generatedClasses = [];
var excludedClasses = [];
for(const subPackage of subPackages) {
  if(!(subPackage.slice(-5) == '.java') && !(subPackage.slice(-3) == '.kt')) {
    const activities = fs.readdirSync(appBasePath+'/'+subPackage);

    for (const activity of activities) {
      var activityName;
      if (activity.slice(-5) === '.java') {
        // .java file
        activityName = activity.slice(0, -5);
      } else {
        // .kt file
        activityName = activity.slice(0, -3);
      }

      // create path for test file
      const filePath = testBasePath+"/"+subPackage+"/"+activityName+'Test.kt';

      // try removing previous generated files
      try {
        fs.accessSync(filePath, fs.F_OK);
        fs.unlinkSync(filePath);
      } catch (e) {
      }

      // only generate test file if not part of exclude list + if contains Activity in name
      if ((!(excludeClasses.indexOf(activityName) > -1)) && activityName.includes("Activity")) {
        // create directories for package
        if (!fs.existsSync(testBasePath+"/"+subPackage)){
            fs.mkdirSync(testBasePath+"/"+subPackage);
        }
        // write test
        fs.writeFileSync(filePath, ejsConversionTask([activityName, subPackage]));
        generatedClasses.push(activityName);
      }else{
        excludedClasses.push(activityName);
      }
    }
  }
}

for(const generatedClass of generatedClasses){
  console.log(generatedClass+"Test");
}

console.log("\nFinished generating " + generatedClasses.length + " activity sanity tests, excluded " + excludeClasses.length + " classes.\n");