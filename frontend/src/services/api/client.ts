import createClient, { Middleware } from "openapi-fetch";
import type { paths } from "./types";
import { getToken, removeToken } from "../token";

export const api = createClient<paths>()

const tokenInterceptor: Middleware = {
    async onRequest({ request }) {
        const token = getToken()
        if (token) {
            request.headers.set('Authorization', `Bearer ${token}`);
        }

        return request;
    },
    async onResponse({ response }) {
        if (response.status === 401) {
            removeToken()
        }
    }
}

api.use(tokenInterceptor);