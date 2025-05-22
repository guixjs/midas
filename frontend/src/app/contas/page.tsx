"use client";
import { useState } from 'react';
import Link from 'next/link';
import './contas.css';

interface Conta {
  id: number;
  nome: string;
  banco: string;
  saldo: number;
  cor: string;
}

interface FormData {
  nome: string;
  banco: string;
  saldo: string;
  cor: string;
}

export default function Contas() {
    // Estado para armazenar as contas
    const [contas, setContas] = useState<Conta[]>([
        // Dados de exemplo
        { id: 1, nome: 'Conta Corrente', banco: 'Nubank', saldo: 2500.75, cor: '#8A2BE2' },
        { id: 2, nome: 'Poupança', banco: 'Banco do Brasil', saldo: 15000.50, cor: '#FFD700' },
        { id: 3, nome: 'Investimentos', banco: 'XP Investimentos', saldo: 7800.25, cor: '#32CD32' },
        { id: 4, nome: 'Carteira', banco: 'Dinheiro Físico', saldo: -150.30, cor: '#FF6347' }
    ]);

    // Estado para o formulário
    const [formData, setFormData] = useState<FormData>({
        nome: '',
        banco: '',
        saldo: '',
        cor: '#fbc02d'
    });

    // Estado para controlar se estamos editando ou criando
    const [editingId, setEditingId] = useState<number | null>(null);

    // Função para lidar com mudanças no formulário
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // Função para salvar uma nova conta ou atualizar existente
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
        if (editingId) {
            // Atualizar conta existente
            setContas(contas.map(conta => 
                conta.id === editingId ? { ...formData, id: editingId, saldo: parseFloat(formData.saldo) } : conta
            ));
            setEditingId(null);
        } else {
            // Adicionar nova conta
            const newConta: Conta = {
                id: Date.now(),
                nome: formData.nome,
                banco: formData.banco,
                saldo: parseFloat(formData.saldo),
                cor: formData.cor
            };
            setContas([...contas, newConta]);
        }

        // Limpar formulário
        setFormData({
            nome: '',
            banco: '',
            saldo: '',
            cor: '#fbc02d'
        });
    };

    // Função para editar uma conta
    const handleEdit = (conta: Conta) => {
        setFormData({
            nome: conta.nome,
            banco: conta.banco,
            saldo: conta.saldo.toString(),
            cor: conta.cor
        });
        setEditingId(conta.id);
        
        // Rolar para o formulário
        document.querySelector('.contas-form-container')?.scrollIntoView({ behavior: 'smooth' });
    };

    // Função para excluir uma conta
    const handleDelete = (id: number) => {
        if (window.confirm('Tem certeza que deseja excluir esta conta?')) {
            setContas(contas.filter(conta => conta.id !== id));
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
                                    />
                                    <div className="color-preview" style={{ backgroundColor: formData.cor }}></div>
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
                                    <div className="conta-item" key={conta.id}>
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
                                                onClick={() => handleDelete(conta.id)}
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