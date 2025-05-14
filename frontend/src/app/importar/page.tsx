import "./import.css"

export default function Importar() {
    return (
        <div className="dashboard">
            <nav className="navLinks">
                <img src="imgs/c590d997-6027-4020-b020-a8bafdb03a44.png" alt="" width={150} />

                <a href="#"> Home </a>
                <a href="#"> Importar </a>
                <a href="#"> Relatórios</a>
            </nav>
            <div className="navIcones">
                <img className="butao" src="imgs/settings_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="settings" />
                <img className="butao" src="imgs/search_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="search" />
                <img
                    src="imgs/393b31d598db67553a2b00d95c6746ae43dfdc1f (1).png"
                    alt=""
                    className="profile-pic" />
            </div>

            <main className="botoes">
                <button className="btn">Enviar extrato</button>
                <button className="btn">Enviar</button>
            </main>
        </div>
    );
}