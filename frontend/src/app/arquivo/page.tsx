import "./arquivo.css"

export default function arquivo() {
    return (
        <div className="dashboard">
            <nav className="navLinks">
                <img src="imgs/logo.png" alt="" width={150} />

                <a href="#"> Home </a>
                <a href="#"> Importar </a>
                <a href="#"> Relatórios</a>
            </nav>
            <div className="fundo">
                <div className="modal-conteiner">
                    <div className="cxs">
                        <h3>Informações</h3>
                        <label>Nome</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>CPF</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Senha</label>
                        <input type="text" />
                    </div>

                    <div className="cxs">
                        <h3>Contatos</h3>
                        <label> E-mail</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Telefone</label>
                        <input type="text" />
                    </div>

                    <div className="cxs">
                        <h3>Endereço</h3>
                        <label>CEP</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Rua</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Estado</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Bairro</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Número</label>
                        <input type="text" />
                    </div>
                    <div className="cxs">
                        <label>Cidade</label>
                        <input type="text" />
                    </div>


                </div>
            </div>

            <div className="navIcones">
                <img className="butao" src="imgs/settings_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="settings" />
                <img className="butao" src="imgs/search_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="search" />
                <img
                    src="imgs/393b31d598db67553a2b00d95c6746ae43dfdc1f (1).png"
                    alt=""
                    className="profile-pic" />
            </div>
        </div>
    );
}