"use client";

import "./import.css"




export default function manual() {
    return (
        <div className="dashboard">
            <nav className="navbar">
                <img src="imgs/logo.png" alt="" width={150} />
                <div className="nav-left">

                    <button className="nav-button" onClick={() => window.location.href = '/home'}>Home</button>
                    <button className="nav-button  active" onClick={() => window.location.href = '/importar'}>Importar</button>
                    <button className="nav-button " onClick={() => window.location.href = '/relatorio'}>Relatórios</button>
                </div>
                <div className="nav-right">
                    <img className="butao" src="imgs/settings_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="settings" />
                    <img className="butao" src="imgs/search_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="search" />
                    <img className="perfil butao" src="imgs/393b31d598db67553a2b00d95c6746ae43dfdc1f (1).png" alt="" />
                </div>
            </nav>

            <main className="botoes">
                <button className="btn">Enviar extrato</button>
                <button className="btn">Enviar</button>
            </main>
        </div>
    );
}