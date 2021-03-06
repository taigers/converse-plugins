# Setup

- Create a new java project using maven (you may use other build tools like gradle, ant). This will create a new folder `my-converse-plugin` in your current folder. You may change the name in the `-DartifactId` option in the command below to have a different project name.

  ```shell 
  mvn archetype:generate \
      -DgroupId=com.your.company \
      -DartifactId=my-converse-plugin \
      -DarchetypeArtifactId=maven-archetype-quickstart \
      -DinteractiveMode=false
  ```

     

- All plugins should be built using the maven-shade plugin. This is to ensure that any libraries that the plugin uses will not interfere or conflict with the Converse application. Add the following configuration to your pom.xml file to enable this build plugin.

  ```xml
  <build>
    <plugins>
        <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.2.2</version>
        <executions>
            <!-- Run shade goal on package phase -->
            <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <filters>
                <filter>
                    <artifact>*:*</artifact>
                    <excludes>
                    <exclude>META-INF/*</exclude>
                    </excludes>
                </filter>
                </filters>
                <artifactSet>
                <excludes>
                    <exclude>com.taiger.converse:delivery-middleware-common:*</exclude>
                </excludes>
                </artifactSet>
                <transformers>
                <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                    <resource>META-INF/spring.handlers</resource>
                </transformer>
                <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                    <resource>META-INF/spring.schemas</resource>
                </transformer>
                <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                    <resource>META-INF/spring.factories</resource>
                </transformer>
                </transformers>
            </configuration>
            </execution>
        </executions>
        </plugin>
    </plugins>
  </build>
  ```

- Go to [Converse Plugins](https://github.com/taigers/converse-plugins) repository. Download `middleare-adaptor-common-0.0.2-SNAPSHOT.jar` from the `libs` folder. This library contains the necessary building blocks to get started with Service Actions. Get in touch with our developers to get the latest version.

- Install this plugin to your local maven repository. Run this in your terminal. Asssuming you've downloaded the jar to your `~/Downloads` folder. Remember to replace `<your-username>` with your actual username.
  ```sh
  mvn install:install-file \
      -Dfile=/Users/<your-username>/Downloads/middleware-adaptor-common-0.0.2-SNAPSHOT.jar \
      -DgroupId=com.taiger.converse \
      -DartifactId=middleware-adaptor-common \
      -Dversion=0.0.2-SNAPSHOT \
      -Dpackaging=jar \
      -DgeneratePom=true
  ```

- Add the following dependency to the `<dependencies>` block in the generated project's `pom.xml` file
    ```xml
    <dependency>
        <groupId>com.taiger.converse</groupId>
        <artifactId>middleware-adaptor-common</artifactId>
        <version>0.0.2-SNAPSHOT</version>
    </dependency>
    ```

- You may add any dependencies or build plugins required for your plugin in the pom.xml.

