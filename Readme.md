# About Selenium Grid Installation in a Docker in EC2 Machine

## Steps:

### 1.  Installition Commands



```bash
  sudo yum update -y
```
- Updates all installed packages to their latest available versions. The -y flag answers "yes" to any prompts during the update process.

```bash
sudo amazon-linux-extras install -y docker
```

- Installs Docker on the Amazon Linux instance.
```bash
sudo service docker start
```
- Starts the Docker service.


```bash
sudo usermod -a -G docker ec2-user 
```

- Adds the ec2-user to the docker group, allowing the user to run Docker commands without sudo.

```bash
sudo chkconfig docker on
```
- Configures Docker to start automatically at boot.

```bash
sudo yum install -y git  
```
- Installs the Git version control system.

```bash
  sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
```
- Downloads the latest Docker Compose binary for your system and places it in /usr/local/bin, making it executable.


```bash
sudo chmod +x /usr/local/bin/docker-compose
```
- Makes the downloaded Docker Compose binary executable.

```bash
docker-compose version 
```
- Checks the Docker Compose version to ensure it's installed correctly.

```bash
sudo curl -L https://raw.githubusercontent.com/selimcim/grid/main/docker-compose.yml -o /home/ec2-user/docker-compose.yml 
```
- Downloads a Docker Compose configuration file and saves it to the /home/ec2-user directory.

```bash
sudo reboot 
```
- Reboots the Amazon Linux instance, allowing all the changes to take effect.

```bash
docker-compose up --scale chrome=3 --remove-orphans
```
- Starts the services defined in your docker-compose.yml file, scales the "chrome" service to have three containers, and removes any containers that are not part of the current Compose file configuration.

- Please make sure you have a valid docker-compose.yml file in the current directory and that it defines a service named "chrome" for this command to work as intended.
### 2. Prepare YML file:

- While we are preparing yaml file for GRID we need to ad below script:

```yaml
version: "3"
services: 
  selenium-hub:
    image: selenium/hub:latest
    container_name: selenium-hub
    ports:           
      - "4442:4442"
      - "4443:4443"
      - "4444:4444"
    environment:
      - SELENIUM_HUB_HOST=hub
      - SELENIUM_HUB_PORT=4444
      - GRID=true
  chrome:
    image: selenium/node-chrome:latest
    shm_size: 256mb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443
      - SE_NODE_MAX_SESSIONS=1
      - SE_NODE_OVERRIDE_MAX_SESSIONS=true
      - SE_NODE_SESSION_TIMEOUT=1000
      - VNC_NO_PASSWORD=1

```



### 3. Setup For Project: 

- In order to call grid from our project we need to add case in our Driver class I added new code block below:

```java
case "remote":
                    ChromeOptions options1 = new ChromeOptions();
                    options1.addArguments("--start-maximized");
                    //  options1.addArguments("--start-fullscreen");

                    URL url = null;
                    try {

                        String urlOfGrid = System.getProperty("GRID_URL") == null ? ConfigurationReader.get("GRID_URL") :
                                "http://52.51.39.105:4444/wd/hub" ;
                        url = new URL(urlOfGrid);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    //   System.setProperty("webdriver.http.factory", "jdk-http-client");
                    driverPool.set(new RemoteWebDriver(url, options1));
                    //mvn test -Denvironment=dev -Dbrowser=remote -Dcucumber.filter.tags=@tournament
                    break;
```

