# Quarkus Crud with Postgres & GraphQL

This project is aimed to quickly test the stack :
- Quarkus (https://quarkus.io/)
- SmallRye GraphQL (https://quarkus.io/guides/microprofile-graphql)
- Hibernate Panache (https://quarkus.io/guides/hibernate-orm-panache)
- Database initialisation with Flyway (https://quarkus.io/guides/flyway)
- Quarkus Basic Auth (https://quarkus.io/guides/security-built-in-authentication#basic-auth)

## Clone & build

- Tests are executed on an h2 database
- Simply `mvn clean install` it

## Run it

You can run the app with a local postgres database by
- running `docker run --name some-postgres -p 5432:5432 -e DATABASE_PASSWORD=mysecretpassword -d postgres`
- `mvn quarkus:dev`

## GraphQl queries

```graphql
# Visit http://localhost:8080/graphql/schema.graphql to view graphql schema

query getEmployees {
  employee {
     name
     email
  }
}

# Sample Mutations available for users : adminUser : 
mutation addEmployee {
  createEmployee(employee: {
      name: "Anand",
    email: "Anand@dakd.com"
  	}
  )
  {
    name
  }
}
```