A simple book store website I once made for class in high school.

Requirements:
* Java 11 or newer
* MySQL

Once you create an empty database, run `createdb.sql` on it to set up the tables the app will use.

Build:
```
gradle build
```

Run: (replace USER etc. with actual data)
```
java -jar build/quarkus-app/quarkus-run.jar \
	-Ddb.user=USER \
	-Ddb.pass=PASSWORD \
	-Ddb.host=HOST \
	-Ddb.port=PORT \
	-Ddb.database=DATABASE
```
The app is running at `http://localhost:8080`.
