//  This function used to clone the github code

def call (String url, String branch) {
    git url: "${url}", branch: "${branch}"
} 

