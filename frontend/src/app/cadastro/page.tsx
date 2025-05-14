"use client";

import "./page_cad.css";
import { useState } from "react";
import Link from 'next/link';


export default function cadastro() {

    const [mostrarSenha, setMostrarSenha] = useState(false);

    const [cpf, setCpf] = useState("");
    const formatarCPF = (valor) => {
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
            <div className="logo_lateral">
                <img src="/imgs/maomidas.png" alt="Logo Midas" width={800} />
            </div>

            <div className="container">
                <div className="titulo"> <p>CADASTRO</p> </div>

                <div className="form">

                    <input type="text" placeholder="Nome Completo" />
                    <div className="ladin">
                        <input type="text" value={cpf} onChange={(e) => setCpf(formatarCPF(e.target.value))} placeholder="Digite seu CPF" maxLength={14} inputMode="numeric" />
                        <input type="text" placeholder="Telefone" />
                    </div>
                    <input type="text" placeholder="Email" />
                    <input type="text" placeholder="Senha" />
                    <input type="text" placeholder="Confirmar Senha" />

                </div>
                <button className="submit" type="submit" onClick={() => window.location.href = '/page'}>Cadastre-se</button>

            </div>

        </div>
    );
}
