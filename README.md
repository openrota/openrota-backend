[![Verify Build Workflow](https://github.com/openrota/openrota-backend/actions/workflows/verify.yaml/badge.svg)](https://github.com/openrota/openrota-backend/actions/workflows/verify.yaml)

# Quarkus Crud with Postgres & GraphQL

This project is aimed to quickly test the stack :

- Quarkus (https://quarkus.io/)
- SmallRye GraphQL (https://quarkus.io/guides/microprofile-graphql)
- Hibernate Panache (https://quarkus.io/guides/hibernate-orm-panache)
- Database initialisation with Flyway (https://quarkus.io/guides/flyway)
- Quarkus Basic Auth (https://quarkus.io/guides/security-built-in-authentication#basic-auth)

## Clone & build

- Tests are executed on an H2 database
- Simply run command `mvn clean install`

## Run it

#### Run in Dev mode

You can run the app in local environment with command `mvn compile quarkus:dev`. Dev mode uses H2 database.

#### Generate and Run JAR

You can run the app with a local postgres database by

- running `docker run --name some-postgres -p 5432:5432 -e DATABASE_PASSWORD=mysecretpassword -d postgres`
- Run `mvn clean install` in root of your project folder. It generates JAR in `target/` folder.
- Run the JAR with `java -jar shareNwork-{$version}-SNAPSHOT-runner.jar`

To build production JAR simply run `mvn clean package -Pproduction`

---

**NOTE**

In order to run the mailer, add `-Dquarkus.mailer.from=${email} -Dquarkus.mailer.username=${username} -Dquarkus.mailer.password=${password}`
after `mvn compile quarkus:dev` or `java -jar shareNwork-{$version}-SNAPSHOT-runner.jar` . You can find instructions to configure the app password here: https://quarkus.io/guides/mailer-reference#gmail-specific-configuration

---

## Testing H2

The project uses H2 as default database

- visit http://localhost:8080/h2/ after running in dev mode
- when prompted, provide url - jdbc:h2:mem:employeerostering

## GraphQl queries

```graphql
# Visit http://localhost:8080/graphql/schema.graphql to view graphql schema
query getEmployees {
  sharedResource {
     totalExperience
    skillProficiencySet{
      name
      id
    }
  }
}

# Sample Mutations
mutation addEmployee {
  createSharedResource(resource: {
      firstName: "dadada",
      emailId: "anand@easa.com"
      totalExperience: "12",
      skillProficiencySet: {
        id: 1,
        name: "react"
      }
  
    }
  )
  {
    id,
    emailId
  }
}
```
