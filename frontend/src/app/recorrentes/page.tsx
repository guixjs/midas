"use client";
import { useState } from 'react';
import './recorrentes.css';

export default function Recorrentes() {
    const [descricao, setDescricao] = useState("");
    const [valor, setValor] = useState("");
    const [categoria, setCategoria] = useState("");
    const [frequencia, setFrequencia] = useState("mensal");
    const [tipo, setTipo] = useState("despesa");
    
    const [transacoesSelecionadas, setTransacoesSelecionadas] = useState<number[]>([]);
    const [mostrarConfirmacao, setMostrarConfirmacao] = useState(false);

    const toggleSelecao = (id: number) => {
        if (transacoesSelecionadas.includes(id)) {
            setTransacoesSelecionadas(transacoesSelecionadas.filter(item => item !== id));
        } else {
            setTransacoesSelecionadas([...transacoesSelecionadas, id]);
        }
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
       
        if (!descricao || !valor || !categoria || !frequencia) {
            alert("Por favor, preencha todos os campos obrigatórios");
            return;
        }
        
        //  lógica para enviar os dados para o back
        console.log("Dados da transação recorrente:", { 
            descricao, 
            valor, 
            categoria, 
            frequencia, 
            tipo 
        });
        
        setDescricao("");
        setValor("");
        setCategoria("");
        setFrequencia("mensal");
        setTipo("despesa");
    };

    const formatarValor = (valor: string) => {
        valor = valor.replace(/\D/g, "");
        if (valor === "") return "";
        
        valor = (parseInt(valor) / 100).toFixed(2);
        return valor.replace(".", ",");
    };

    const handleCadastrarTransacao = () => {
        if (transacoesSelecionadas.length === 0) {
            alert("Por favor, selecione pelo menos uma transação para cadastrar.");
            return;
        }
        
        setMostrarConfirmacao(true);
    };

    const confirmarCadastro = async () => {
        try {
            //  lógica para enviar as transações para o backend
            console.log("Transações cadastradas:", transacoesSelecionadas);
            
            setMostrarConfirmacao(false);
            
            setTransacoesSelecionadas([]);
            
            alert("Transações cadastradas com sucesso!");
            
            // Recarregar a página
            window.location.reload();
        } catch (error) {
            console.error("Erro ao cadastrar transações:", error);
            alert("Erro ao cadastrar transações. Por favor, tente novamente.");
        }
    };

    const cancelarCadastro = () => {
        setMostrarConfirmacao(false);
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
                                <div className="recorrentes-checkbox">
                                    <input 
                                        type="checkbox" 
                                        id="transacao-1"
                                        checked={transacoesSelecionadas.includes(1)}
                                        onChange={() => toggleSelecao(1)}
                                    />
                                </div>
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
                                <div className="recorrentes-checkbox">
                                    <input 
                                        type="checkbox" 
                                        id="transacao-2"
                                        checked={transacoesSelecionadas.includes(2)}
                                        onChange={() => toggleSelecao(2)}
                                    />
                                </div>
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
                            <button className="add-transaction-button" onClick={handleCadastrarTransacao}>
                                <span className="add-icon">+</span>
                                Cadastrar Transação
                            </button>
                        </div>
                    </div>
                </div>
            </main>

            {/* Modal de confirmação */}
            {mostrarConfirmacao && (
                <div className="modal-overlay">
                    <div className="modal-confirmacao">
                        <h3>Confirmar Cadastro</h3>
                        <p>Tem certeza que deseja cadastrar {transacoesSelecionadas.length} transação(ões)?</p>
                        <div className="modal-buttons">
                            <button className="btn-cancelar" onClick={cancelarCadastro}>Cancelar</button>
                            <button className="btn-confirmar" onClick={confirmarCadastro}>Confirmar</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}