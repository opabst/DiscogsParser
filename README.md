# DiscogsParser
Java project to efficiently parse the database dumps from the discogs music database and marketplace (https://www.discogs.com/)

## Contents
- parsers for all 4 dump files (artists, labels, masters, releases), based on StAX
- unit tests to verfiy the correctness of the implemented parsers
- relational database schema to store the data
- database backend to write the parsed files into a database 

## Folders
- db/
  - sqlscripts to create or drop the database
- src/
  - java files for parsing the xml dumps and writing the parsed data to the database
