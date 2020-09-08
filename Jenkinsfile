node {

print "****  START: SET  VARIABLES  : START ****"
    def mavenHome   = tool 'MVN363'
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
    sh "sudo docker push ${dockerRepo}/${imageName}"

  stage 'DEPLOY TO SWARM'

    sh "chmod +x deploy.sh && ./deploy.sh ${dockerSwarm} ${dockerRepo}/${imageName} ${serviceName}"
       
}
