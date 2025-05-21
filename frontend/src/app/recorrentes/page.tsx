"use client";
import { useState } from 'react';
import './recorrentes.css';

export default function Recorrentes() {
    const [descricao, setDescricao] = useState("");
    const [valor, setValor] = useState("");
    const [categoria, setCategoria] = useState("");
    const [dataInicio, setDataInicio] = useState("");
    const [dataFim, setDataFim] = useState("");
    const [frequencia, setFrequencia] = useState("mensal");
    const [tipo, setTipo] = useState("despesa");

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
       
        if (!descricao || !valor || !categoria || !dataInicio || !frequencia) {
            alert("Por favor, preencha todos os campos obrigatórios");
            return;
        }
        
        // Aqui será adicionado a lógica para enviar os dados para o backend
        console.log("Dados da transação recorrente:", { 
            descricao, 
            valor, 
            categoria, 
            dataInicio, 
            dataFim, 
            frequencia, 
            tipo 
        });
        
        // Limpar formulário após envio
        setDescricao("");
        setValor("");
        setCategoria("");
        setDataInicio("");
        setDataFim("");
        setFrequencia("mensal");
        setTipo("despesa");
    };

    // Formatação de valor monetário
    const formatarValor = (valor: string) => {
        valor = valor.replace(/\D/g, "");
        if (valor === "") return "";
        
        valor = (parseInt(valor) / 100).toFixed(2);
        return valor.replace(".", ",");
    };

    return (
        <div className="dashboard">
            <nav className="navbar">
                <div className="logo-container">
                    <img src="imgs\MIDA$NOME.svg" alt="Midas" className="logo" />
                </div>
                
                <div className="nav-links">
                    <a href="/dashboard" className="nav-link">Dashboard</a>
                    <a href="/transacoes" className="nav-link">Transações</a>
                    <a href="/recorrentes" className="nav-link active">Recorrentes</a>
                    <a href="/categorias" className="nav-link">Categorias</a>
                    <a href="/contas" className="nav-link">Contas</a>
                </div>
                
                <div className="user-profile">
                    <div className="user-icon">
                        <span>👤</span>
                    </div>
                </div>
            </nav>

            <main className="recorrentes-container">
                <div className="recorrentes-header">
                    <h1>Transações Recorrentes</h1>
                    <p>Cadastre suas receitas e despesas que se repetem regularmente</p>
                </div>

                <div className="recorrentes-content">
                    <div className="recorrentes-form-container">
                        <h2>Nova Transação Recorrente</h2>
                        <form className="recorrentes-form" onSubmit={handleSubmit}>
                            <div className="form-row">
                                <div className="form-group">
                                    <label htmlFor="descricao">Descrição:*</label>
                                    <input 
                                        type="text" 
                                        id="descricao" 
                                        value={descricao}
                                        onChange={(e) => setDescricao(e.target.value)}
                                        placeholder="Ex: Assinatura Netflix"
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="valor">Valor:*</label>
                                    <input 
                                        type="text" 
                                        id="valor" 
                                        value={valor}
                                        onChange={(e) => setValor(formatarValor(e.target.value))}
                                        placeholder="R$ 0,00"
                                    />
                                </div>
                            </div>
                            
                            <div className="form-row">
                                <div className="form-group">
                                    <label htmlFor="categoria">Categoria:*</label>
                                    <select 
                                        id="categoria" 
                                        value={categoria}
                                        onChange={(e) => setCategoria(e.target.value)}
                                    >
                                        <option value="">Selecione uma categoria</option>
                                        <option value="moradia">Moradia</option>
                                        <option value="transporte">Transporte</option>
                                        <option value="alimentacao">Alimentação</option>
                                        <option value="lazer">Lazer</option>
                                        <option value="saude">Saúde</option>
                                        <option value="educacao">Educação</option>
                                        <option value="servicos">Serviços</option>
                                        <option value="outros">Outros</option>
                                    </select>
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
                            </div>
                            
                            <div className="form-row">
                                <div className="form-group">
                                    <label htmlFor="dataInicio">Data de Início:*</label>
                                    <input 
                                        type="date" 
                                        id="dataInicio" 
                                        value={dataInicio}
                                        onChange={(e) => setDataInicio(e.target.value)}
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="dataFim">Data de Término: <small>(opcional)</small></label>
                                    <input 
                                        type="date" 
                                        id="dataFim" 
                                        value={dataFim}
                                        onChange={(e) => setDataFim(e.target.value)}
                                    />
                                </div>
                            </div>
                            
                            <div className="form-row">
                                <div className="form-group">
                                    <label htmlFor="frequencia">Repetir valor:*</label>
                                    <select 
                                        id="frequencia" 
                                        value={frequencia}
                                        onChange={(e) => setFrequencia(e.target.value)}
                                    >
                                        <option value="sim">Sim</option>
                                        <option value="não">Não</option>
                                        
                                    </select>
                                </div>
                            </div>
                            
                            <button type="submit" className="save-button">Salvar Transação Recorrente</button>
                        </form>
                    </div>
                    
                    <div className="recorrentes-list-container">
                        <h2>Suas Transações Recorrentes</h2>
                        <div className="recorrentes-list">
                            <div className="recorrentes-item">
                                <div className="recorrentes-item-header">
                                    <h3>Assinatura Netflix</h3>
                                    <span className="recorrentes-valor despesa">R$ 39,90</span>
                                </div>
                                <div className="recorrentes-item-details">
                                    <span className="recorrentes-categoria">Lazer</span>
                                    <span className="recorrentes-frequencia">Mensal</span>
                                    <span className="recorrentes-data">Desde: 01/01/2023</span>
                                </div>
                                <div className="recorrentes-item-actions">
                                    <button className="action-btn edit">Editar</button>
                                    <button className="action-btn delete">Excluir</button>
                                </div>
                            </div>
                            
                            <div className="recorrentes-item">
                                <div className="recorrentes-item-header">
                                    <h3>Salário</h3>
                                    <span className="recorrentes-valor receita">R$ 3.500,00</span>
                                </div>
                                <div className="recorrentes-item-details">
                                    <span className="recorrentes-categoria">Trabalho</span>
                                    <span className="recorrentes-frequencia">Mensal</span>
                                    <span className="recorrentes-data">Desde: 01/01/2023</span>
                                </div>
                                <div className="recorrentes-item-actions">
                                    <button className="action-btn edit">Editar</button>
                                    <button className="action-btn delete">Excluir</button>
                                </div>
                            </div>
                        </div>
                        
                        <div className="add-transaction-container">
                            <button className="add-transaction-button">
                                <span className="add-icon">+</span>
                                Cadastrar Transação
                            </button>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
}