import { api } from "./api"

function App() {
  return (
    <>
      <h1 className='text-4xl font-bold text-center mb-2'>Welcome to Vite</h1>
      <div className='text-center'>
        <button
          className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
          onClick={() =>
            api.POST('/api/auth/token', {
              headers: {
                Authorization: 'Basic ' + btoa('fujis:password'),
              }
            }).then(res => {
              const token = res.data?.token
              if (!token) {
                console.warn('No token found in response')
              }

              return api.GET('/api/todos', {
                headers: {
                  Authorization: `Bearer ${token}`
                }
              })
            }).then(res => {
              console.log(res.data)
            })
          }
        >
          Run API request
        </button>
      </div>
    </>
  )
}

export default App
