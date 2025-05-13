"use client";

import "./home.css";


export default function Home() {
    return (
        <div className="dashboard">

            <nav className="navbar">
                <img src="imgs/c590d997-6027-4020-b020-a8bafdb03a44.png" alt="" width={150} />
                <div className="nav-left">

                    <button className="nav-button active" onClick={() => window.location.href = '/home'}>Home</button>
                    <button className="nav-button " onClick={() => window.location.href = '/importar'}>Importar</button>
                    <button className="nav-button ">Relatórios</button>
                </div>
                <div className="nav-right">
                    <img className="butao" src="imgs/settings_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="settings" />
                    <img className="butao" src="imgs/search_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="search" />
                    <img className="perfil butao" src="imgs/393b31d598db67553a2b00d95c6746ae43dfdc1f (1).png" alt="" />
                </div>
            </nav>


            <main className="main-content">
                <div className="buttons">
                    <button className="action-button" onClick={() => window.location.href = '/importar'} >Enviar Extrato</button>
                    <button className="action-button" onClick={() => window.location.href = '/manual'}>Cadastro manual das transações</button>
                </div>

                <div className="card-grid four">
                    <div className="card" />
                    <div className="card" />
                    <div className="card" />
                    <div className="card" />
                </div>

                <div className="card-grid two">
                    <div className="card large" />
                    <div className="card large" />
                </div>
            </main>

            <footer className="footer" />
        </div>
    );

}