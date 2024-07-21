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
            }).then((res) => console.log(res))
          }
        >
          login
        </button>
      </div>
    </>
  )
}

export default App
