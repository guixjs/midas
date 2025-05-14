import "./arquivo.css"

export default function arquivo() {
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
                <div className="DivTabela">
                    <h2>Transações  Importadas</h2>

                    <thead className="tabela">
                        <tr>
                            <th className="tbl">Data</th>
                            <th className="tbl">Descrição</th>
                            <th className="tbl">Valor (R$)</th>
                            <th className="tbl">Categoria</th>
                        </tr>
                    </thead>
                    <tr><td>12-03-2022</td><td>Transação A</td><td>R$200.00</td><td></td></tr>
                    <tr><td>12-03-2022</td><td>Transação A</td><td>R$200.00</td><td></td></tr>
                    <tr><td>12-03-2022</td><td>Transação A</td><td>R$200.00</td><td></td></tr>
                    <tr><td>12-03-2022</td><td>Transação A</td><td>R$200.00</td><td></td></tr>
                    <tbody>

                    </tbody>

                </div>
                <button className="btn"> Editar Trasnsações</button>
            </main>

        </div>
    );
}