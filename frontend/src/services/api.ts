import { UUID } from "crypto";

const apiUrl = process.env.NEXT_PUBLIC_API_BASE_URL;


export interface UserCadastro{
nome: string,
cpf:string,
email:string,
telefone:string,
senha:string
}

export const login = async (cpf: string, senha: string) => {
  const response = await fetch(`${apiUrl}/user/auth`, {
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
  const response = await fetch(`${apiUrl}/user/new`,{
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
    const response = await fetch(`${apiUrl}${endpoint}`,{
      headers:{
        "Authorization": `Bearer ${token}`,
      },
    });
    return handleResponse(response);
  },
  getEspecifica:async (endpoint:string, id: UUID|number) =>{
    const token = localStorage.getItem("token");
    const response = await fetch(`${apiUrl}${endpoint}${id}`,{
      headers:{
        "Authorization": `Bearer ${token}`,
      },
    });
    return handleResponse(response);
  },
  post: async (endpoint:string, body:any)=>{
    const token = localStorage.getItem("token");
    const response = await fetch(`${apiUrl}${endpoint}`,{
      method: "POST",
      headers:{
        "Content-type":"application/json",
        "Authorization":`Bearer ${token}`,
      },
      body: JSON.stringify(body),
    });
    return handleResponse(response)
  },
  put: async (endpoint:string, id: number|UUID, body:any) =>{
    const token = localStorage.getItem("token");
    const response = await fetch(`${apiUrl}${endpoint}/${id}`,{
      method: "PUT",
      headers:{
        "Content-type":"application/json",
        "Authorization":`Bearer ${token}`,
      },
      body: JSON.stringify(body),
  });
    return handleResponse(response)
  },
  delete: async (endpoint: string, id: number|UUID) =>{
    const token = localStorage.getItem("token");
    const response = await fetch(`${apiUrl}${endpoint}/${id}`,{
      method:"DELETE",
      headers:{
        "Authorization": `Bearer ${token}`,
      }
    })
  },
  postArquivo: async (endpoint: string, formData: FormData) => {
    const token = localStorage.getItem("token");
    const response = await fetch(`${apiUrl}${endpoint}`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
      },
      body: formData,
    });

    if (!response.ok) {
        throw new Error(await response.text());
      }
      
  }
}

const handleResponse = async (response: Response) => {
  if (!response.ok) {
    const error = await response.text();
    throw new Error(error);
  }
  return response.json();
};



