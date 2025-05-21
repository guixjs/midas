"use client";
import { useState } from 'react';
import './categorias.css';

export default function Categorias() {
    const [nome, setNome] = useState("");
    const [tipo, setTipo] = useState("despesa");
    const [cor, setCor] = useState("#4299e1");

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
        // Validações básicas
        if (!nome) {
            alert("Por favor, preencha o nome da categoria");
            return;
        }
        
        // Aqui você pode adicionar a lógica para enviar os dados para o backend
        console.log("Dados da categoria:", { 
            nome, 
            tipo,
            cor
        });
        
        // Limpar formulário após envio
        setNome("");
        setTipo("despesa");
        setCor("#4299e1");
    };

    // Lista de categorias de exemplo
    const categorias = [
        { id: 1, nome: "Alimentação", tipo: "despesa", cor: "#e53e3e" },
        { id: 2, nome: "Transporte", tipo: "despesa", cor: "#dd6b20" },
        { id: 3, nome: "Moradia", tipo: "despesa", cor: "#38a169" },
        { id: 4, nome: "Lazer", tipo: "despesa", cor: "#3182ce" },
        { id: 5, nome: "Salário", tipo: "receita", cor: "#805ad5" },
        { id: 6, nome: "Freelance", tipo: "receita", cor: "#d53f8c" },
    ];

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
                                <label htmlFor="tipo">Tipo:*</label>
                                <div className="radio-group">
                                    <label className={`radio-label ${tipo === 'despesa' ? 'active' : ''}`}>
                                        <input 
                                            type="radio" 
                                            name="tipo" 
                                            value="despesa"
                                            checked={tipo === 'despesa'}
                                            onChange={() => setTipo('despesa')}
                                        />
                                        Despesa
                                    </label>
                                    <label className={`radio-label ${tipo === 'receita' ? 'active' : ''}`}>
                                        <input 
                                            type="radio" 
                                            name="tipo" 
                                            value="receita"
                                            checked={tipo === 'receita'}
                                            onChange={() => setTipo('receita')}
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
                                    />
                                    <span className="color-preview" style={{ backgroundColor: cor }}></span>
                                </div>
                            </div>
                            
                            <button type="submit" className="save-button">Salvar Categoria</button>
                        </form>
                    </div>
                    
                    <div className="categorias-list-container">
                        <h2>Suas Categorias</h2>
                        <div className="categorias-list">
                            {categorias.map((categoria) => (
                                <div key={categoria.id} className="categoria-item">
                                    <div className="categoria-color" style={{ backgroundColor: categoria.cor }}></div>
                                    <div className="categoria-info">
                                        <h3>{categoria.nome}</h3>
                                        <span className={`categoria-tipo ${categoria.tipo}`}>
                                            {categoria.tipo === 'despesa' ? 'Despesa' : 'Receita'}
                                        </span>
                                    </div>
                                    <div className="categoria-actions">
                                        <button className="action-btn edit">✎</button>
                                        <button className="action-btn delete">🗑️</button>
                                    </div>
                                </div>
                            ))}
                        </div>
                        
                        
                    </div>
                </div>
            </main>
        </div>
    );
}