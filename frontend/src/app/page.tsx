"use client";

import "./page.css";
import { useState } from "react";
import Link from 'next/link';
import { login } from '../services/api'

export default function Home() {
  const [mostrarSenha, setMostrarSenha] = useState(false);
  const [cpf, setCpf] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);

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

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    
    try{
      const {token, expires_in} = await login(cpf,senha)
      localStorage.setItem('token', token);
      localStorage.setItem('token_expires', expires_in.toString());

      window.location.href ='/transacoes'

    }catch(error){
      console.error("Erro no login", error)
      alert("Falha no login")
    }finally{
      setLoading(false);
    }
  };

  return (
    <div className="global">
      <div className="logo-section">
        <div className="logo-container">
          <img src="/imgs/maomidas.svg" alt="Logo Midas" />
        </div>
      </div>

      <div className="form-section">
        <div className="form-container">
          <h1 className="login-title">Login</h1>
          <p className="login-subtitle">Acesse sua conta para gerenciar suas finanças</p>

          <form className="login-form" onSubmit={handleLogin}>
            <div className="input-group">
              <span className="input-icon">👤</span>
              <input 
                type="text" 
                placeholder="Digite seu CPF" 
                value={cpf}
                onChange={(e) => setCpf(formatarCPF(e.target.value))}
                maxLength={14}
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
                       
            <button type="submit" className="login-button" disabled={loading}>
              {loading ? 'Carregando...' : 'Entrar'}
            </button>
            
            <div className="signup-link">
              <p>Não possui uma conta? <Link href="/cadastro" className="signup-text">Cadastre-se</Link></p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
