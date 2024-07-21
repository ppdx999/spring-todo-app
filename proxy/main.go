package main

import (
    "log"
    "net/http"
    "net/http/httputil"
    "net/url"
    "strings"
)

func main() {
    backendURL, _ := url.Parse("http://localhost:8080")
    frontendURL, _ := url.Parse("http://localhost:3000")

    proxy := func(w http.ResponseWriter, r *http.Request) {
        if strings.HasPrefix(r.URL.Path, "/api/") {
            httputil.NewSingleHostReverseProxy(backendURL).ServeHTTP(w, r)
        } else {
            httputil.NewSingleHostReverseProxy(frontendURL).ServeHTTP(w, r)
        }
    }

    http.HandleFunc("/", proxy)
    log.Println("Starting proxy server on :8081")
    log.Fatal(http.ListenAndServe(":8081", nil))
}
