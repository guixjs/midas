"use client";
import { useState, useEffect } from 'react';
import './recorrentes.css';
import { api } from '@/services/api';

interface Recorrente {
  id: number;
  descricao: string;
  dataTransacao: string;
  valor: number | null;
  tipoTransacao: string;
  idCategoria: number;
  idConta: number;
  idCartao: number | null;
}

interface Categoria {
  id: number;
  nome: string;
}

export default function Recorrentes() {
    const [descricao, setDescricao] = useState("");
    const [valor, setValor] = useState("");
    const [categoria, setCategoria] = useState("");
    const [repetirValor, setRepetirValor] = useState(true);
    const [tipo, setTipo] = useState("despesa");
    const [idConta, setIdConta] = useState(1);
    const [selectedIds, setSelectedIds] = useState<number[]>([]);
    const [valoresTemporarios, setValoresTemporarios] = useState<Record<number, number | null>>({});
    const [recorrentes, setRecorrentes] = useState<Recorrente[]>([]);
    const [categorias, setCategorias] = useState<Categoria[]>([]);
    const [mostrarConfirmacao, setMostrarConfirmacao] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const carregarDados = async () => {
            try {
                setLoading(true);
                
                // Carrega categorias e recorrentes em paralelo
                const [categoriasData, recorrentesData] = await Promise.all([
                    api.get('/category'),
                    api.get('/recurring')
                ]);
                
                setCategorias(categoriasData);
                setRecorrentes(recorrentesData);
                
            } catch (err:any) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        
        carregarDados();
    }, []);

    const handleSelect = (id: number) => {
        setSelectedIds(prev => 
            prev.includes(id)
            ? prev.filter(itemId => itemId !== id)
            : [...prev, id]
        );
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        
        try {
            setLoading(true);
            const valorNumerico = parseFloat(valor.replace(",", "."));
            
            await api.post('/recurring/new', {
                descricao,
                tipoTransacao: tipo.toUpperCase(),
                valor: valorNumerico,
                idCategoria: parseInt(categoria),
                idConta,
                idCartao: null,
                repetirValor
            });
            
            // Recarrega a lista de recorrentes
            const novaLista = await api.get('/recurring');
            setRecorrentes(novaLista);
            
            // Limpa o formulário
            setDescricao("");
            setValor("");
            setCategoria("");
            setRepetirValor(true);
            setTipo("despesa");
            
            alert("Transação recorrente cadastrada com sucesso!");
        } catch (err:any) {
            setError(err.message);
            alert("Erro ao cadastrar transação recorrente: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    const formatarValor = (valor: string) => {
        valor = valor.replace(/\D/g, "");
        if (valor === "") return "";
        
        valor = (parseInt(valor) / 100).toFixed(2);
        return valor.replace(".", ",");
    };

    const handleCadastrarTransacao = () => {
        if (selectedIds.length === 0) {
            alert("Por favor, selecione pelo menos uma transação para cadastrar.");
            return;
        }
        
        setMostrarConfirmacao(true);
    };

    const confirmarCadastro = async () => {
        try {
            setLoading(true);
            
            const transacoesParaConverter = recorrentes
                .filter(rec => selectedIds.includes(rec.id))
                .map(rec => {
                    // Usa o valor temporário se existir, senão o valor original
                    const valorFinal = rec.valor === null 
                        ? valoresTemporarios[rec.id] 
                        : rec.valor;
                    
                    if (valorFinal === null) {
                        throw new Error(`A transação "${rec.descricao}" não tem valor definido`);
                    }
                    
                    return {
                        ...rec,
                        valor: valorFinal
                    };
                });
            
            await api.post('/recurring/many', transacoesParaConverter);
            
            setMostrarConfirmacao(false);
            setSelectedIds([]);
            
            const novaLista = await api.get('/recurring');
            setRecorrentes(novaLista);
            
            alert("Transações convertidas com sucesso!");
        } catch (err:any) {
            setError(err.message);
            alert("Erro ao converter transações: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    const cancelarCadastro = () => {
        setMostrarConfirmacao(false);
    };

    const getNomeCategoria = (id: number) => {
        const categoriaEncontrada = categorias.find(c => c.id === id);
        return categoriaEncontrada ? categoriaEncontrada.nome : `Categoria ${id}`;
    };

    return (
        <div className="dashboard">
            <nav className="navbar">
                <div className="logo-container">
                    <img src="imgs/MIDA$NOME.svg" alt="Midas" className="logo" />
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
                        <span></span>
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
                                        placeholder="Ex: Assinatura"
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
                                        {categorias.map(cat => (
                                            <option key={cat.id} value={cat.id}>{cat.nome}</option>
                                        ))}
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
                                    <label htmlFor="repetirValor">Repetir valor:</label>
                                    <div className="checkbox-group">
                                        <label className="checkbox-label">
                                            <input 
                                                type="checkbox" 
                                                id="repetirValor"
                                                checked={repetirValor}
                                                onChange={(e) => setRepetirValor(e.target.checked)}
                                            />
                                            Manter o mesmo valor nas próximas transações
                                        </label>
                                    </div>
                                </div>
                            </div>
                            
                            <button type="submit" className="save-button" disabled={loading}>
                                {loading ? 'Salvando...' : 'Salvar Transação Recorrente'}
                            </button>
                        </form>
                    </div>
                    
                    <div className="recorrentes-list-container">
                        <h2>Suas Transações Recorrentes</h2>
                        <div className="recorrentes-list">
                            {loading && <p>Carregando...</p>}
                            {error && <p className="error-message">{error}</p>}
                            
                            {recorrentes.map((recorrente) => (
                                <div className="recorrentes-item" key={recorrente.id}>
                                    <div className="recorrentes-checkbox">
                                    <input
                                        type="checkbox"
                                        checked={selectedIds.includes(recorrente.id)}
                                        onChange={() => handleSelect(recorrente.id)}
                                        disabled={recorrente.valor === null && valoresTemporarios[recorrente.id]==null}
                                    />
                                    </div>
                                    <div className="recorrentes-item-header">
                                        <h3>{recorrente.descricao}</h3>
                                        {recorrente.valor !== null ? (
                                            <span className={`recorrentes-valor ${recorrente.tipoTransacao.toLowerCase() === 'despesa' ? 'despesa' : 'receita'}`}>
                                                R$ {Math.abs(recorrente.valor).toFixed(2).replace(".", ",")}
                                            </span>
                                        ) : (
                                            <div className="valor-temporario-container">
                                                <input
                                                    type="text"
                                                    placeholder="Defina o valor"
                                                    value={valoresTemporarios[recorrente.id]?.toFixed(2).replace(".", ",") || ""}
                                                    onChange={(e) => {
                                                        const valor = parseFloat(e.target.value.replace(/\D/g, "")) / 100 || null;
                                                        setValoresTemporarios(prev => ({
                                                            ...prev,
                                                            [recorrente.id]: valor
                                                        }));
                                                    }}
                                                    className="input-valor-temporario"
                                                />
                                                <span className="hint-text">* Valor apenas para esta conversão</span>
                                            </div>
                                        )}
                                    </div>
                                    <div className="recorrentes-item-details">
                                        <span className="recorrentes-categoria">{getNomeCategoria(recorrente.idCategoria)}</span>
                                    </div>
                                    <div className="recorrentes-item-actions">
                                        <button className="action-btn edit">Editar</button>
                                        <button className="action-btn delete">Excluir</button>
                                    </div>
                                    {recorrente.valor === null && (
                                        <div className="recorrentes-warning">
                                            Defina um valor para esta transação antes de convertê-la
                                        </div>
                                    )}
                                </div>
                            ))}
                        </div>
                        
                        <div className="add-transaction-container">
                            <button 
                                className="add-transaction-button" 
                                onClick={handleCadastrarTransacao}
                                disabled={loading || selectedIds.length === 0}
                            >
                                <span className="add-icon">+</span>
                                {loading ? 'Processando...' : 'Cadastrar Transação'}
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
                        <p>Tem certeza que deseja cadastrar {selectedIds.length} transação(ões)?</p>
                        <div className="modal-buttons">
                            <button className="btn-cancelar" onClick={cancelarCadastro} disabled={loading}>Cancelar</button>
                            <button className="btn-confirmar" onClick={confirmarCadastro} disabled={loading}>
                                {loading ? 'Confirmando...' : 'Confirmar'}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}