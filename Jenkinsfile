node {

print "****  START: SET  VARIABLES  : START ****"
    def mavenHome   = "${tool 'MVN363'}/bin/mvn"
    def branchName  = "${BRANCH_NAME}"
    def buildNum    = "${BUILD_NUMBER}"
    def serviceName = "demo-service"
    def imageName   = "demo-api"
	def dockerSwarm = "${SWARM_MANAGER_HOST}"
    def dockerRepo  = "${DOCKER_REPOSITORY}"


print "****  START: SCM  CHECKOUT  : START ****" 
    stage 'CHECKOUT'
    checkout scm
print "****  END: SCM  CHECKOUT  : END ****"


  stage 'BUILD APPLICATION'
      
      sh "${mavenHome} clean"
      sh "${mavenHome}  package"    
     
  stage 'BUILD DOCKER IMAGE'
      sh "sudo docker build -t ${imageName}  ."

    stage 'TAG/PUSH IMAGE'

    sh "sudo docker tag ${imageName} ${dockerRepo}/${imageName}"
	sh "sudo docker login -u ${username} -p ${password}"
    sh "sudo docker push ${dockerRepo}/${imageName}"

  stage 'DEPLOY TO DOCKER'
    try {
            sh "sudo docker rm -f ${serviceName}"
        }
    catch (exc) {
            echo 'Unable to remove container or container does not exits'
            
        }

    sh "sudo docker run -dit --name ${serviceName} -p 8080:8080 ${dockerRepo}/${imageName}"
       
}
