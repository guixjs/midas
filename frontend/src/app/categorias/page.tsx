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
    descricao?:string,
	tipoTransacao: "DESPESA" | "RECEITA",
    cor:string
}


export default function Categorias() {
    const [tipo, setTipo] = useState<"DESPESA" | "RECEITA">("DESPESA");
    const [categorias,setCategorias] = useState<Categoria[]>([])
    const [carregando,setCarregando] = useState(true)
    const [editingId,setEditingId] = useState<number | null>(null);
    const [formData, setFormData] = useState <NovaCategoria>({
        nome:'',
        descricao: '',
        cor: '',
        tipoTransacao: "DESPESA" 
    })

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

        if (!formData.nome) {
            alert("Por favor, preencha o nome da categoria");
            return;
        }


        try{
            const payload ={
                nome: formData.nome,
                descricao: formData.descricao,
                tipoTransacao: formData.tipoTransacao,
                cor: formData.cor
            }

            if(editingId){
                await api.put(`/category`,editingId,payload)
            }else{
                await api.post("/category/new",payload)

            }
            console.log("categoria cadastrada")
            await carregarCategorias()

            setFormData({
                nome:'',
                tipoTransacao:"DESPESA",
                descricao: '',
                cor: ''
            })

            setEditingId(null)

        }catch(error){
            console.log(error)
            alert("Erro ao criar categoria")
        }
    };

    const handleEdit = (categoria: Categoria) =>{
        setFormData({
            nome: categoria.nome,
            descricao: categoria.descricao,
            cor: categoria.cor,
            tipoTransacao: categoria.tipoTransacao
        })
        setEditingId(categoria.id)
    }

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

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
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
                        <span></span>
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
                        <h2>{editingId ? 'Editar Categoria' :
                            'Adicionar Categoria'}</h2>
                        <form className="categorias-form" onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="nome">Nome da Categoria:*</label>
                                <input 
                                    type="text" 
                                    id="nome" 
                                    name="nome"
                                    value={formData.nome}
                                    onChange={handleChange}
                                    placeholder="Ex: Alimentação"
                                />
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="descricao">Descrição:</label>
                                <input 
                                    type="text" 
                                    id="descricao"
                                    name='descricao' 
                                    value={formData.descricao}
                                    onChange={handleChange}
                                    placeholder="Opcional"
                                />
                            </div>
                            
                            <div className="form-group">
                                 <label htmlFor="tipo">Tipo:*</label>
                                    <div className="radio-group">
                                        <label className={`radio-label ${formData.tipoTransacao === 'DESPESA' ? 'active' : ''}`}>
                                            <input 
                                                type="radio" 
                                                name="tipoTransacao" 
                                                value="DESPESA"
                                                checked={formData.tipoTransacao === 'DESPESA'}
                                                onChange={handleChange}
                                            />
                                            Despesa
                                        </label>
                                        <label className={`radio-label ${formData.tipoTransacao === 'RECEITA' ? 'active' : ''}`}>
                                            <input 
                                                type="radio" 
                                                name="tipoTransacao" 
                                                value="RECEITA"
                                                checked={formData.tipoTransacao === 'RECEITA'}
                                                onChange={handleChange}
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
                                        name='cor'
                                        value={formData.cor}
                                        onChange={handleChange}
                                        className="color-input"
                                    />
                                    <span>Selecione uma cor para sua categoria</span>
                                </div>
                            </div>
                            
                            <button type="submit" className="save-button">
                                {editingId ? "Atualizar Categoria": "Salvar Categoria"}
                            </button>
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
                                            {categoria.id > 7 && (
                                                <>
                                                <button className="action-btn edit"
                                                onClick={()=>handleEdit(categoria)}
                                                >✎</button>
                                                <button 
                                                    className="action-btn delete"
                                                    onClick={() => handleDelete(categoria.id)}
                                                >
                                                    🗑️
                                                </button>
                                                </>
                                            )}
                                            
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