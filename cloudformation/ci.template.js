var cf = require('@mapbox/cloudfriend');

module.exports = {
AWSTemplateFormatVersion: '2010-09-09',
  Resources: {
    User: {
      Type: 'AWS::IAM::User',
      Properties: {
        Policies: [
          {
            PolicyName: 'publish-android',
            PolicyDocument: {
              Statement: [
                {
                  Action: ['publish-android:*'],
                  Effect: 'Allow',
                  Resource: '*'
                }
              ]
            }
          },
          {
            PolicyName: 'upload-sdk-registry',
            PolicyDocument: {
              Statement: [
                {
                  Action: ['s3:ListBucket'],
                  Effect: 'Allow',
                  Resource: [
                    'arn:aws:s3:::mapbox-api-downloads-production',
                    'arn:aws:s3:::mapbox-api-downloads-staging'
                  ]
                },
                {
                  Action: ['s3:PutObject'],
                  Effect: 'Allow',
                  Resource: [
                    'arn:aws:s3:::mapbox-api-downloads-production/v2/*',
                    'arn:aws:s3:::mapbox-api-downloads-staging/v2/*'
                  ]
                }
              ]
            }
          },
          {
            PolicyName: 'get-signing-key',
            PolicyDocument: {
              Statement: [
                {
                  Action: ['s3:GetObject'],
                  Effect: 'Allow',
                  Resource: ['arn:aws:s3:::mapbox/android/signing-credentials/secring.gpg']
                }
              ]
            }
          }
        ]
      }
    },
    AccessKey: {
      Type: 'AWS::IAM::AccessKey',
      Properties: {
        UserName: cf.ref('User')
      }
    }
  },
  Outputs: {
    AccessKeyId: { Value: cf.ref('AccessKey') },
    SecretAccessKey: { Value: cf.getAtt('AccessKey', 'SecretAccessKey') }
  }
};