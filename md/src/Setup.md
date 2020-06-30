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

- Add the following dependency to the `<dependencies>` block. This library contains the necessary building blocks to get started with Service Actions. Get in touch with our developers to get the latest version.

    ```xml
    <dependency>
        <groupId>com.taiger.converse</groupId>
        <artifactId>converse-connector-engine-common</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```

- You may add any dependencies or build plugins required for your plugin in the pom.xml.

