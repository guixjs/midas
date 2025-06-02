"use client";
import { useState, useEffect } from 'react';
import './recorrentes.css';
import { api } from '@/services/api';


interface Recorrente {
  id: number;
  descricao: string;
  dataTransacao: string;
  valor: number | null;
  tipoTransacao: "DESPESA"|"RECEITA";
  idCategoria: number;
  idConta: number;
  idCartao: number | null;
  repetirValor: boolean;
}

interface NovaRecorrente {
  descricao: string;
  valor: number | null;
  tipoTransacao: "DESPESA"|"RECEITA";
  idCategoria: number | undefined;
  idConta: number;
  repetirValor: boolean
}

interface Categoria {
  id: number;
  nome: string;
}

export default function Recorrentes() {
    const [selectedIds, setSelectedIds] = useState<number[]>([]);
    const [valoresTemporarios, setValoresTemporarios] = useState<Record<number, number | null>>({});
    const [recorrentes, setRecorrentes] = useState<Recorrente[]>([]);
    const [categorias, setCategorias] = useState<Categoria[]>([]);
    const [mostrarConfirmacao, setMostrarConfirmacao] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [editingId,setEditingId] = useState<number | null>(null);
    const [formData, setFormData] = useState<NovaRecorrente>({
        descricao: '',
        valor: 0,
        tipoTransacao: 'DESPESA',
        idCategoria: undefined,
        idConta: 1,
        repetirValor: false
    })

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            setLoading(true);
            
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
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value, type } = e.target;
        const checked = (e.target as HTMLInputElement).checked;
        
        setFormData({
            ...formData,
            [name]: type === 'checkbox' ? checked : value
        });
    };
    const handleSelect = (id: number) => {
        setSelectedIds(prev => 
            prev.includes(id)
            ? prev.filter(itemId => itemId !== id)
            : [...prev, id]
        );
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

         if (!formData.tipoTransacao) {
            alert("Por favor, selecione o tipo de transação (Despesa ou Receita)");
            return;
        }
        try {
            setLoading(true);

            const payload = {
                descricao: formData.descricao,
                tipoTransacao: formData.tipoTransacao,
                valor: Number(formData.valor),
                idCategoria: Number(formData.idCategoria),
                idConta: formData.idConta,
                repetirValor: formData.repetirValor
            }
            if(editingId){
                await api.put('/recurring',editingId,payload)
            }else{
                await api.post('/recurring/new',payload)
            }
                        
            const novaLista = await api.get('/recurring');
            setRecorrentes(novaLista);
            
            setFormData({
                descricao: '',
                valor: 0,
                tipoTransacao: 'DESPESA',
                idCategoria: undefined,
                idConta: 1,
                repetirValor: false
            })
            
            alert("Transação recorrente cadastrada com sucesso!");
        } catch (err:any) {
            console.log(err)
            console.log(err.message)
            setError(err.message);
            // alert("Erro ao cadastrar transação recorrente: " + err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleEdit = (recorrente: Recorrente) =>{
        setFormData({
            descricao: recorrente.descricao,
            valor: recorrente.valor,
            tipoTransacao: recorrente.tipoTransacao,
            idCategoria: recorrente.idCategoria,
            idConta: recorrente.idConta,
            repetirValor: recorrente.repetirValor
        })
        setEditingId(recorrente.id)
    }

    const handleDelete = async (id: number) => {
        if(!confirm('Tem certeza que deseja apagar esta conta?')){
            return;
        }
        try{
            await api.delete(`/recurring`,id)
            await carregarDados()
        }catch(error){
            console.error(error);
            setError("Erro ao excluir conta");
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
                        <h2>{editingId ? "Nova Transação Recorrente":"Editar Transação Recorrente"}</h2>
                        <form className="recorrentes-form" onSubmit={handleSubmit}>
                            <div className="form-row">
                                <div className="form-group">
                                    <label htmlFor="descricao">Descrição:*</label>
                                    <input 
                                        type="text" 
                                        id="descricao"
                                        name='descricao'
                                        value={formData.descricao}
                                        onChange={handleChange}
                                        placeholder="Ex: Assinatura"
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="valor">Valor:*</label>
                                    <input 
                                        type="text" 
                                        id="valor"
                                        name='valor'
                                        value={formData.valor !== null ? formData.valor.toString() : ''} 
                                        onChange={handleChange}
                                        placeholder="R$ 0,00"
                                    />
                                </div>
                            </div>
                            
                            <div className="form-row">
                                <div className="form-group">
                                    <label htmlFor="categoria">Categoria:*</label>
                                    <select 
                                        id="categoria" 
                                        name='idCategoria'  
                                        value={formData.idCategoria || ''}
                                        onChange={handleChange}
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
                                        <label className={`radio-label ${formData.tipoTransacao === 'DESPESA' ? 'active' : ''}`}>
                                            <input 
                                                type="radio" 
                                                name="tipoTransacao"
                                                value="DESPESA" // ← MAIÚSCULAS
                                                checked={formData.tipoTransacao === 'DESPESA'}
                                                onChange={(e) => setFormData({...formData, tipoTransacao: e.target.value as "DESPESA"|"RECEITA"})}
                                            />
                                            Despesa
                                        </label>
                                        <label className={`radio-label ${formData.tipoTransacao === 'RECEITA' ? 'active' : ''}`}>
                                            <input 
                                                type="radio" 
                                                name="tipoTransacao"
                                                value="RECEITA" // ← MAIÚSCULAS
                                                checked={formData.tipoTransacao === 'RECEITA'}
                                                onChange={(e) => setFormData({...formData, tipoTransacao: e.target.value as "DESPESA"|"RECEITA"})}
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
                                                name="repetirValor"
                                                checked={formData.repetirValor}
                                                onChange={handleChange}
                                            />
                                            Manter o mesmo valor nas próximas transações
                                        </label>
                                    </div>
                                </div>
                            </div>
                            
                            <button type="submit" className="save-button" disabled={loading}>
                                {editingId ? "Atualizar Transação Recorrente": "Salvar Recorrente"}
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
                                        <button className="action-btn edit" onClick={()=>handleEdit(recorrente)}>Editar</button>
                                        <button className="action-btn delete" onClick={()=>handleDelete(recorrente.id)}>Excluir</button>
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