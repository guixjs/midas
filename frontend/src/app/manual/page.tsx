"use client";

import "./manual.css";

export default function manual() {
    return (
        <div className="dashboard">
            <nav className="navbar">
                <img src="imgs/logo.png" alt="" width={150} />
                <div className="nav-left">
                    <button className="nav-button active" onClick={() => window.location.href = '/home'}>Home</button>
                    <button className="nav-button " onClick={() => window.location.href = '/importar'}>Importar</button>
                    <button className="nav-button " onClick={() => window.location.href = '/relatorio'}>Relatórios</button>
                </div>
                <div className="nav-right">
                    <img className="butao" src="imgs/settings_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="settings" />
                    <img className="butao" src="imgs/search_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="search" />
                    <img className="perfil butao" src="imgs/393b31d598db67553a2b00d95c6746ae43dfdc1f (1).png" alt="" />
                </div>
            </nav>
            <main className="form-container">
                <form className="transaction-form">
                    <div className="form-row">
                        <div className="form-group">
                            <label htmlFor="valor">Valor:*</label>
                            <input type="text" id="valor" name="valor" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="categoria">Categoria:*</label>
                            <input type="text" id="categoria" name="categoria" />
                        </div>
                    </div>
                    <div className="form-row">
                        <div className="form-group">
                            <label htmlFor="data">Data:*</label>
                            <input type="date" id="data" name="data" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="tipo">Tipo:*</label>
                            <input type="text" id="tipo" name="tipo" />
                        </div>
                    </div>
                    <div className="form-group">
                        <label htmlFor="descricao">Descrição: <small>(opcional)</small></label>
                        <textarea id="descricao" name="descricao" rows={4} />
                    </div>
                    <div className="form-row">
                        <label>
                            <input type="radio" name="variacao" value="fixa" />
                            Fixa
                        </label>
                        <label>
                            <input type="radio" name="variacao" value="variavel" />
                            Variável
                        </label>
                    </div>
                    <button type="submit" className="save-button">Salvar</button>
                </form>
            </main>
        </div >
    );
}