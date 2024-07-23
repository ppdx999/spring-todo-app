import { generateToken } from "./services/auth"
import { listTodos } from "./services/todo"

function App() {
  return (
    <>
      <h1 className='text-4xl font-bold text-center mb-2'>Welcome to Vite</h1>
      <div className='text-center'>
        <button
          className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'
          onClick={() => {
            generateToken('fujis', 'password')
            .then(() => {
              console.log('Token generated. Check the local storage to see the token')

              return listTodos()
            }).then((todos) => {
              console.log('Todos:', todos)
            })
          }}
        >
          Run API request
        </button>
      </div>
    </>
  )
}

export default App
