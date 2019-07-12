# Building

    * To build the project use following command: `mvn clean install`

# Running

    * After building the application run following command to start it:

        - `java -DinputSource=YOUR_INPUT_SOURCE_PATH -DoutputSource=YOUR_OUTPUT_SOURCE_PATH -jar target/wikiTransformer-1.0-SNAPSHOT.jar`

    * to configure the data input source change "-DinputSource=>>YOUR_INPUT_SOURCE_PATH<<" property with your path
    in the aforementioned command.

    * to configure the data output source change "-DoutputSource=>>YOUR_OUTPUT_SOURCE_PATH<<" property with your path
    in the aforementioned command.

$ java -DinputSource=C:/dev/projects/wikiTransformer/input -DoutputSource=C:/dev/projects/wikiTransformer/output -jar target/wikiTransformer-1.0-SNAPSHOT.jar
