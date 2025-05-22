"use client";
import { useEffect, useState } from 'react';
import './categorias.css';
import { api } from '@/services/api';

interface Categoria {
  id: number;
  nome: string;
  descricao?: string;
  cor: string;
  tipoTransacao: "DESPESA" | "RECEITA";
}

interface NovaCategoria{
    nome:string,
    descricao:string,
	tipoTransacao: "DESPESA" | "RECEITA",
    cor:string
}


export default function Categorias() {
    const [nome, setNome] = useState("");
    const [tipo, setTipo] = useState<"DESPESA" | "RECEITA">("DESPESA");
    const [cor, setCor] = useState("#4299e1");
    const [descricao,setDescricao] = useState("")
    const [categorias,setCategorias] = useState<Categoria[]>([])
    const [carregando,setCarregando] = useState(true)

    useEffect(()=>{
            carregarCategorias();
        },[])

        const carregarCategorias = async()=>{
            try{
                setCarregando(true)
                const response = await api.get("/category");
                setCategorias(response)

            }catch(error){
                alert("Erro ao carregar categorias")
            }finally{
                setCarregando(false)
            }
        }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!nome) {
            alert("Por favor, preencha o nome da categoria");
            return;
        }


        try{
            await api.post("/category/new",{
                nome,
                descricao,
                tipoTransacao: tipo,
                cor
            });
            console.log("categoria cadastrada")
            carregarCategorias()

            setNome("");
            setTipo("DESPESA");
            setCor("#4299e1");
            setDescricao("")

        }catch(error){
            console.log(error)
            alert("Erro ao criar categoria")
        }
    };

    const handleDelete = async (id: number) => {
        if (!confirm("Tem certeza que deseja excluir esta categoria?")) return;
        
        try {
            await api.delete("/category",id);
            carregarCategorias();
            alert("Categoria excluída com sucesso!");
        } catch (error) {
            console.error("Erro ao excluir categoria:", error);
            alert("Erro ao excluir categoria");
        }
    };


    return (
        <div className="dashboard">
            <nav className="navbar">
                <div className="logo-container">
                    <img src="/imgs/MIDA$NOME.svg" alt="Midas" className="logo" />
                </div>
                
                <div className="nav-links">
                    <a href="/dashboard" className="nav-link">Dashboard</a>
                    <a href="/transacoes" className="nav-link">Transações</a>
                    <a href="/recorrentes" className="nav-link">Recorrentes</a>
                    <a href="/categorias" className="nav-link active">Categorias</a>
                    <a href="/contas" className="nav-link">Contas</a>
                </div>
                
                <div className="user-profile">
                    <div className="user-icon">
                        <span>👤</span>
                    </div>
                </div>
            </nav>

            <main className="categorias-container">
                <div className="categorias-header">
                    <h1>Gerenciamento de Categorias</h1>
                    <p>Organize suas finanças com categorias personalizadas</p>
                </div>

                <div className="categorias-content">
                    <div className="categorias-form-container">
                        <h2>Nova Categoria</h2>
                        <form className="categorias-form" onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="nome">Nome da Categoria:*</label>
                                <input 
                                    type="text" 
                                    id="nome" 
                                    value={nome}
                                    onChange={(e) => setNome(e.target.value)}
                                    placeholder="Ex: Alimentação"
                                />
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="descricao">Descrição:</label>
                                <input 
                                    type="text" 
                                    id="descricao" 
                                    value={descricao}
                                    onChange={(e) => setDescricao(e.target.value)}
                                    placeholder="Opcional"
                                />
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="tipo">Tipo:*</label>
                                <div className="radio-group">
                                    <label className={`radio-label ${tipo === 'DESPESA' ? 'active' : ''}`}>
                                        <input 
                                            type="radio" 
                                            name="tipo" 
                                            value="DESPESA"
                                            checked={tipo === 'DESPESA'}
                                            onChange={() => setTipo('DESPESA')}
                                        />
                                        Despesa
                                    </label>
                                    <label className={`radio-label ${tipo === 'RECEITA' ? 'active' : ''}`}>
                                        <input 
                                            type="radio" 
                                            name="tipo" 
                                            value="RECEITA"
                                            checked={tipo === 'RECEITA'}
                                            onChange={() => setTipo('RECEITA')}
                                        />
                                        Receita
                                    </label>
                                </div>
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="cor">Cor:*</label>
                                <div className="color-picker">
                                    <input 
                                        type="color" 
                                        id="cor" 
                                        value={cor}
                                        onChange={(e) => setCor(e.target.value)}
                                        className="color-input"
                                    />
                                    <span>Selecione uma cor para sua categoria</span>
                                </div>
                            </div>
                            
                            <button type="submit" className="save-button">Salvar Categoria</button>
                        </form>
                    </div>
                    
                    <div className="categorias-list-container">
                        <h2>Suas Categorias</h2>
                        {carregando ? (
                            <p>Carregando categorias...</p>
                        ) : (
                            <div className="categorias-list">
                                {categorias.map((categoria) => (
                                    <div key={categoria.id} className="categoria-item">
                                        <div className="categoria-color" style={{ backgroundColor: categoria.cor }}></div>
                                        <div className="categoria-info">
                                            <h3>{categoria.nome}</h3>
                                            <span className={`categoria-tipo ${categoria.tipoTransacao.toLowerCase()}`}>
                                                {categoria.tipoTransacao === 'DESPESA' ? 'Despesa' : 'Receita'}
                                            </span>
                                            {categoria.descricao && <p>{categoria.descricao}</p>}
                                        </div>
                                        <div className="categoria-actions">
                                            <button className="action-btn edit">✎</button>
                                            <button 
                                                className="action-btn delete"
                                                onClick={() => handleDelete(categoria.id)}
                                            >
                                                🗑️
                                            </button>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>
            </main>
        </div>
    );
}