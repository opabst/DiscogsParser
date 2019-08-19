# DiscogsParser
DiscogsParser is a java-project based on the StAX-api to parse the xml-based database dumps
from the discogs music database and marketplace (https://www.discogs.com/)

The project contains four parsers, one for each dump file, to parse the information of artists, labels, masters and
releases. Additionally writers are implemented to store the parsed informations in a relational database, in this case
PostgreSQL. SQL-scripts are provided to create the required database schemas and scripts to transform the schema in a more
usable format using integrity constraints and indizes.

## Folders
- db/
  - sqlscripts to create or drop the database and transform the data
- doc/
  - er-diagrams that describe the database schema
- src/
  - java files for parsing the xml dumps and writing the parsed data to the database
