import { api } from "./api"
import { setToken } from "./token"

export const generateToken = async (username: string, password: string) => {
    const response = await api.POST('/api/auth/token',{
        headers: {
            Authorization: 'Basic ' + btoa(`${username}:${password}`)
        }
    })

    if (response.error) {
        // TODO: Implement after adding error response to openapi's schema
    }

    const token = response.data?.token

    if (!token) {
        throw new Error('No token found in response')
    }

    setToken(token)
}