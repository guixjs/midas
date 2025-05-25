"use client";
import { useEffect, useState } from 'react';
import Link from 'next/link';
import './contas.css';
import { api } from '@/services/api';

interface Conta {
  idConta: number;
  nome: string;
  tipoConta: "CARTEIRA" | "CORRENTE" | "POUPANCA";
  banco: string | null;
  saldo: number;
  cor: string;
}

interface FormData {
  nome: string;
  tipoConta: "CARTEIRA" | "CORRENTE" | "POUPANCA";
  banco: string;
  saldo: string;
  cor: string;
}

export default function Contas() {
    // Estado para armazenar as contas
    const [contas, setContas] = useState<Conta[]>([]);
    const [formData, setFormData] = useState<FormData>({
        nome: '',
        tipoConta: "CORRENTE",
        banco: '',
        saldo: '',
        cor: '#fbc02d'
    });
    const [editingId, setEditingId] = useState<number | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);



    useEffect(() => {
        carregarContas();
    }, []);

  const carregarContas = async ()=>{
    try{
        setLoading(true)
        setError(null)
        const response = await api.get(`/account`)
        console.log(response)
        setContas(response)
    }catch(error){
        console.log(error)
        setError("Erro ao carregar contas. Tente recarregar a página.");
        setContas([]);
    }finally{
        setLoading(false)
    }
  };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // Função para salvar uma nova conta ou atualizar existente
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError(null)

        if (!formData.nome || !formData.saldo) {
            setError("Nome e saldo são obrigatórios");
            return;
        }
        
        try{
            const payload = {
                nome: formData.nome,
                tipoConta: formData.tipoConta,
                banco: formData.banco,
                saldo: Number(formData.saldo),
                cor: formData.cor
            }
            if (editingId) {
                await api.put(`/account`,editingId, payload);
            } else {
                await api.post("/account/new", payload);
            }

            await carregarContas();
            setFormData({
                nome: '',
                tipoConta: "CORRENTE",
                banco: '',
                saldo: '',
                cor: '#fbc02d'
            })
            setEditingId(null)

        }catch(error){
            console.log(error)
            setError("Erro ao salvar conta. Verifique os dados e tente novamente.");
        }
    };

    // // Função para editar uma conta
    const handleEdit = (conta: Conta) => {
        setFormData({
            nome: conta.nome,
            tipoConta: conta.tipoConta,
            banco: conta.banco || '',
            saldo: conta.saldo.toString(),
            cor: conta.cor
        });
        setEditingId(conta.idConta);
    };
        

    // // Função para excluir uma conta
    const handleDelete = async (idConta: number) => {
        if(!confirm('Tem certeza que deseja apagar esta conta?')){
            return;
        }
        try{
            await api.delete(`/account`,idConta)
            await carregarContas()
        }catch(error){
            console.error(error);
            setError("Erro ao excluir conta");
        }
    };

    // Função para formatar valor monetário
    const formatCurrency = (value: number) => {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(value);
    };

    return (
        <div className="dashboard">
            <nav className="navbar">
                <div className="logo-container">
                    <img src="/imgs/MIDA$NOME.svg" alt="Logo Midas" className="logo" />
                </div>
                <div className="nav-links">
                    <a href="/dashboard" className="nav-link">Dashboard</a>
                    <a href="/transacoes" className="nav-link">Transações</a>
                    <a href="/recorrentes" className="nav-link">Recorrentes</a>
                    <a href="/categorias" className="nav-link">Categorias</a>
                    <a href="/contas" className="nav-link active">Contas</a>
                </div>
                <div className="user-profile">
                    <div className="user-icon">
                        <span>👤</span>
                    </div>
                </div>
            </nav>

            <div className="contas-container">
                <div className="contas-header">
                    <h1>Gerenciamento de Contas</h1>
                    <p>Adicione e gerencie suas contas bancárias</p>
                </div>

                <div className="contas-content">
                    {/* Formulário para adicionar/editar conta */}
                    <div className="contas-form-container">
                        <h2 className="contas-form-title">
                            {editingId ? 'Editar Conta' : 'Adicionar Nova Conta'}
                        </h2>
                        <form className="contas-form" onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="nome">Nome da Conta</label>
                                <input
                                    type="text"
                                    id="nome"
                                    name="nome"
                                    value={formData.nome}
                                    onChange={handleChange}
                                    placeholder="Ex: Conta Corrente, Poupança..."
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="banco">Instituição Financeira</label>
                                <input
                                    type="text"
                                    id="banco"
                                    name="banco"
                                    value={formData.banco}
                                    onChange={handleChange}
                                    placeholder="Ex: Nubank, Banco do Brasil..."
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="saldo">Saldo Atual</label>
                                <input
                                    type="number"
                                    id="saldo"
                                    name="saldo"
                                    value={formData.saldo}
                                    onChange={handleChange}
                                    placeholder="0.00"
                                    step="0.01"
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="cor">Cor da Conta</label>
                                <div className="color-picker">
                                    <input
                                        type="color"
                                        id="cor"
                                        name="cor"
                                        value={formData.cor}
                                        onChange={handleChange}
                                        className="color-input"
                                    />
                                    <span>Selecione uma cor para identificar sua conta</span>
                                </div>
                            </div>

                            <button type="submit" className="save-button">
                                {editingId ? 'Atualizar Conta' : 'Salvar Conta'}
                            </button>
                        </form>
                    </div>

                    {/* Lista de contas */}
                    <div className="contas-list-container">
                        <h2 className="contas-form-title">Minhas Contas</h2>
                        
                        <div className="contas-list">
                            {contas.length === 0 ? (
                                <p>Nenhuma conta cadastrada. Adicione sua primeira conta!</p>
                            ) : (
                                contas.map(conta => (
                                    <div className="conta-item" key={conta.idConta}>
                                        <div className="conta-color" style={{ backgroundColor: conta.cor }}></div>
                                        <div className="conta-info">
                                            <h3>{conta.nome}</h3>
                                            <div className={`conta-saldo ${conta.saldo >= 0 ? 'positivo' : 'negativo'}`}>
                                                {formatCurrency(conta.saldo)}
                                            </div>
                                            <div className="conta-banco">{conta.banco}</div>
                                        </div>
                                        <div className="conta-actions">
                                            <button 
                                                className="action-btn edit"
                                                onClick={() => handleEdit(conta)}
                                            >
                                                ✎
                                            </button>
                                            <button 
                                                className="action-btn delete"
                                                onClick={() => handleDelete(conta.idConta)}
                                            >
                                                🗑️
                                            </button>
                                        </div>
                                    </div>
                                ))
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}