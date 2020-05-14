# Private API

> Note: All endpoints support localization. To localize your API response, please append `?locale=<locale>` to your queries.

> For your comfort, a few translated articles have been seeded to the database on start.

## Articles

> Note: The following endpoints are protected.

### POST /p/articles

> Create a new article with any translations (n translations).

Usage :

```
$ curl -X POST http://127.0.0.1:8084/p/articles
```

Payload :

```
{
    "slug": "my-slug",
    "translations": [
        {
            "title": "my-en-title",
            "content": "my-en-content",
            "locale": "en"
        },
        {
            "title": "my-fr-title",
            "content": "my-fr-title",
            "locale": "fr"
        },
        ...
    ]
}
```

Response :

```
# 201 - Created
Payload
```

### PUT /p/articles/:slug

> Update an article with any translations (n translations).

Usage :

```
$ curl -X PUT http://127.0.0.1:8084/p/articles/my-slug
```

Payload :

```
{
    "slug": "my-new-slug",
    "translations": [
        {
            "title": "my-new-en-title",
            "content": "my-en-content",
            "locale": "en"
        },
        {
            "title": "my-new-fr-title",
            "content": "my-fr-title",
            "locale": "fr"
        },
        ...
    ]
}
```

Response :

```
# 200 - OK
Payload
```

### DELETE /p/articles/:slug

> Delete an article with his associated translations (n translations).

Usage :

```
$ curl -X DELETE http://127.0.0.1:8084/p/articles/my-slug
```

Response :

```
# 200 - OK
true
```

## Users

> Note: The following endpoints are not protected.

### POST /register

> Register a new user to the application.

Usage :

```
$ curl -X POST http://127.0.0.1:8084/users/register
```

Payload :

```
{
    "firstName": "John",
    "lastName": "Doe",
    "username": "my-username",
    "password": "My-PaSSwOrD"
}
```

> Note: Username must be at least 6, and up to 64 characters. Password must be as least 8 characters.

### POST /login

> Login a user to the application, and return a JWT token.

> Note: All tokens are valid up to 1 hour.

Usage :

```
$ curl -X POST http://127.0.0.1:8084/users/login
```

Payload :

```
{
    "username": "my-username",
    "password": "My-PaSSwOrD"
}
```

Response :

```
# 200 - OK
"my-token"
```

## Errors

> In case the JSON is malformed.

> In case the request payload is invalid.

```
# 400 - Bad Request
{
    "success": "false",
    "message": "Bad Request"
}
```