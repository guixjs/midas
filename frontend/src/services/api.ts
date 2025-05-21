const API_BASE_URL = 'http://localhost:8080';

export interface UserCadastro{
nome: string,
cpf:string,
email:string,
telefone:string,
senha:string
}

export const login = async (cpf: string, senha: string) => {
  const response = await fetch(`${API_BASE_URL}/user/auth`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ cpf, senha }),
  });

  if (!response.ok) {
    throw new Error('Login falhou');
  }

  return await response.json();
};

export const cadastrar = async(novoUser: UserCadastro) =>{
  const response = await fetch(`${API_BASE_URL}/user/new`,{
    method:'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(novoUser)
  });
  if(!response.ok){
    throw new Error('Cadastro falhou');
  }
  return await response.json();
}

export const api = {
  get:async (endpoint:string) =>{
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_BASE_URL}${endpoint}`,{
      headers:{
        "Authorization": `Bearer ${token}`,

      },
    });
    return handleResponse(response);
  },
  post: async (endpoint:string, body:any)=>{
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_BASE_URL}${endpoint}`,{
      method: "POST",
      headers:{
        "Content-type":"application/json",
        "Authorization":`Bearer ${token}`,
      },
      body: JSON.stringify(body),
    });
    return handleResponse(response)
  },
}

const handleResponse = async (response: Response) => {
  if (!response.ok) {
    const error = await response.text();
    throw new Error(error);
  }
  return response.json();
};



