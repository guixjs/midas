"use client";

import "./page.css";
import { useState } from "react";
import Link from 'next/link';





export default function Home() {
  const [mostrarSenha, setMostrarSenha] = useState(false);

  const [cpf, setCpf] = useState("");

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

  return (

    <div className="global">

      <div className="main">
        <div className="container">

          <div className="titulo"><p>ENTRAR</p></div>
          <p className="text-acess">Acesse sua conta para gerenciar suas finanças!</p>
          <div className="formulario">

            <form className="form">
              <div className="infos campo-senha ">
                <input type="text" value={cpf} onChange={(e) => setCpf(formatarCPF(e.target.value))} placeholder="Digite seu CPF" maxLength={14} inputMode="numeric" />
                <input type={mostrarSenha ? "text" : "password"} placeholder="Digite sua senha" />
                <div className="icone-placeholder-senha">
                  <img className="ver-senha" src={mostrarSenha ? "/imgs/visibility_24dp_000000_FILL0_wght400_GRAD0_opsz24.png" : "/imgs/visibility_off_24dp_000000_FILL0_wght400_GRAD0_opsz24.png"} onClick={() => setMostrarSenha(!mostrarSenha)} alt="Ver senha" />
                </div>
              </div>
              <div className="forgot">
                <div className="forgot"> Esqueci minha senha</div>
              </div>
              <div className="confirm-login">
                <button type="button" className="entrar">Entrar</button>
                <p>OU</p>
                <button type="button" className="cod-acess">Usar código de acesso</button>
              </div>
            </form>
            <div className="cadastrar">
              <h3>Não Possui uma conta?</h3>
              <button className="criar-conta" onClick={() => window.location.href = '/cadastro'}>Criar Conta</button>
            </div>
            <img className="Logo" src="./imgs/c590d997-6027-4020-b020-a8bafdb03a44.png" alt="Logo Midas" />
          </div>
        </div>
      </div>
    </div>
  );
}
