# Public API

> Note: All endpoints support localization. To localize your API response, please append `?locale=<locale>` to your queries.

> For your comfort, a few translated articles have been seeded to the database on start.

### GET /articles

> Return all articles.

Usage :

```
$ curl -X GET http://127.0.0.1:8080/articles

# With a custom locale
$ curl -X GET http://127.0.0.1:8080/articles?locale=fr
```

Response :

```
[
    {
        "id": 1,
        "slug": "what-is-bitcoin",
        "title": "What is Bitcoin ?",
        "content": "A cryptocurrency ...",
        "locale": "en"
    },
    ...
]
```

### GET /articles/:slug

> Return an article based on a slug.

Usage :

```
$ curl -X GET http://127.0.0.1:8080/articles/what-is-bitcoin

# With a custom locale
$ curl -X GET http://127.0.0.1:8080/articles/what-is-bitcoin?locale=fr
```

Response :

```
{
    "id": 1,
    "slug": "what-is-bitcoin",
    "title": "What is Bitcoin ?",
    "content": "A cryptocurrency ...",
    "locale": "en"
}
```

### GET /articles/search/:slug

> Search all article based on a slug.

Usage :

```
$ curl -X GET http://127.0.0.1:8080/articles/search/bitcoin

# With a custom locale
$ curl -X GET http://127.0.0.1:8080/articles/search/bitcoin?locale=fr
```

Response :

```
{
    "id": 1,
    "slug": "what-is-bitcoin",
    "title": "What is Bitcoin ?",
    "content": "A cryptocurrency ...",
    "locale": "en"
}
```

## Errors

> In case a locale does not exist.

```
400 - Bad Request
{
    "success": "false",
    "message": "Bad Request"
}
```

> In case an article is Not Found.

```
404 - Not Found
{
    "success": "false",
    "message": "Not Found"
}
```