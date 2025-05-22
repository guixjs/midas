"use client";

import "./page_cad.css";
import { useState, useEffect } from "react";
import Link from 'next/link';
import { cadastrar, UserCadastro } from "@/services/api";



export default function Cadastro() {
    
    const [nome, setNome] = useState("");
    const [cpf, setCpf] = useState("");
    const [email, setEmail] = useState("");
    const [telefone, setTelefone] = useState("");
    const [senha, setSenha] = useState("");
    const [confirmarSenha, setConfirmarSenha] = useState("");
    const [aceitouTermos, setAceitouTermos] = useState(false);
    const [mostrarSenha, setMostrarSenha] = useState(false);
    const [mostrarConfirmarSenha, setMostrarConfirmarSenha] = useState(false);

    // Verificar se os termos foram aceitos ao carregar a página
    useEffect(() => {
        const termosAceitos = localStorage.getItem('termosAceitos') === 'true';
        if (termosAceitos) {
            setAceitouTermos(true);
        }
    }, []);

    // Formatação de CPF
    const formatarCPF = (valor: string) => {
        valor = valor.replace(/\D/g, "").slice(0, 11);

        if (valor.length <= 3)
            return valor;
        if (valor.length <= 6)
            return valor.replace(/(\d{3})(\d+)/, "$1.$2");
        if (valor.length <= 9)
            return valor.replace(/(\d{3})(\d{3})(\d+)/, "$1.$2.$3");
        return valor.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4");
    };

    
    const formatarTelefone = (valor: string) => {
        valor = valor.replace(/\D/g, "").slice(0, 11);
        
        if (valor.length <= 2)
            return valor;
        if (valor.length <= 6)
            return valor.replace(/(\d{2})(\d+)/, "($1) $2");
        return valor.replace(/(\d{2})(\d{5})(\d+)/, "($1) $2-$3");
    };

    
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        
        
        if (!nome || !cpf || !email || !telefone || !senha || !confirmarSenha) {
            alert("Por favor, preencha todos os campos");
            return;
        }
        
        if (senha !== confirmarSenha) {
            alert("As senhas não coincidem");
            return;
        }
        
        if (!aceitouTermos) {
            alert("Você precisa aceitar os termos de uso");
            return;
        }

        const telefoneNumerico = telefone.replace(/\D/g, '');

        const novoUsuario: UserCadastro = {
                nome,
                cpf,
                email,
                telefone:telefoneNumerico,
                senha
            };

        try{
            const response = await cadastrar(novoUsuario)
            console.log("Usuario cadastrado com sucesso!")
            window.location.href = '/';
        }catch(error){
            console.log('Erro no cadastro:', error);
            alert(error instanceof Error ? error.message: "Erro ao cadastrar")
        }
    };

    return (
        <div className="global">
            <div className="logo_lateral">
                <img src="/imgs/maomidas.svg" alt="Logo Midas" width={800} />
            </div>

            <div className="container">
                <h1 className="titulo">Cadastro</h1>
                <p className="subtitulo">Cadastre uma conta para gerenciar suas finanças.</p>

                <form className="form" onSubmit={handleSubmit}>
                    <div className="input-group">
                        <span className="input-icon">👤</span>
                        <input 
                            type="text" 
                            placeholder="Digite seu nome" 
                            value={nome}
                            onChange={(e) => setNome(e.target.value)}
                        />
                    </div>
                    
                    <div className="input-group">
                        <span className="input-icon">🪪</span>
                        <input 
                            type="text" 
                            placeholder="Digite seu CPF" 
                            value={cpf}
                            onChange={(e) => setCpf(formatarCPF(e.target.value))}
                            maxLength={14}
                        />
                    </div>
                    
                    <div className="input-group">
                        <span className="input-icon">✉️</span>
                        <input 
                            type="email" 
                            placeholder="Digite seu email" 
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    
                    <div className="input-group">
                        <span className="input-icon">📱</span>
                        <input 
                            type="text" 
                            placeholder="Digite seu telefone" 
                            value={telefone}
                            onChange={(e) => setTelefone(formatarTelefone(e.target.value))}
                            maxLength={15}
                        />
                    </div>
                    
                    <div className="input-group">
                        <span className="input-icon">🔒</span>
                        <input 
                            type={mostrarSenha ? "text" : "password"} 
                            placeholder="Digite sua senha" 
                            value={senha}
                            onChange={(e) => setSenha(e.target.value)}
                        />
                        <span 
                            className="toggle-password" 
                            onClick={() => setMostrarSenha(!mostrarSenha)}
                        >
                            {mostrarSenha ? "👁️" : "👁️‍🗨️"}
                        </span>
                    </div>
                    
                    <div className="input-group">
                        <span className="input-icon">🔒</span>
                        <input 
                            type={mostrarConfirmarSenha ? "text" : "password"} 
                            placeholder="Confirme sua senha" 
                            value={confirmarSenha}
                            onChange={(e) => setConfirmarSenha(e.target.value)}
                        />
                        <span 
                            className="toggle-password" 
                            onClick={() => setMostrarConfirmarSenha(!mostrarConfirmarSenha)}
                        >
                            {mostrarConfirmarSenha ? "👁️" : "👁️‍🗨️"}
                        </span>
                    </div>
                    
                    <div className="terms-container">
                        <label className="terms-label">
                            <input 
                                type="checkbox" 
                                checked={aceitouTermos}
                                onChange={(e) => setAceitouTermos(e.target.checked)}
                            />
                            <span>Li e concordo com os <Link href="/termos" className="terms-link" target="_blank">termos de uso</Link></span>
                        </label>
                    </div>
                    
                    <button className="submit" type="submit">Cadastrar</button>
                </form>
            </div>
        </div>
    );
}
