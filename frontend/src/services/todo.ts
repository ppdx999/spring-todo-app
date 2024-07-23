import { api } from "./api"

export const listTodos = async () => {
    const response = await api.GET('/api/todos')
    return response.data
}