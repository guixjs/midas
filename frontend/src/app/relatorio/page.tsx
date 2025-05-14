"use client";

import "./relatorio.css";

export default function Relatorio() {
    return (
        <div className="dashboard">
            <nav className="navbar">
                <img src="imgs/logo.png" alt="" width={150} />
                <div className="nav-left">
                    <button className="nav-button " onClick={() => window.location.href = '/home'}>Home</button>
                    <button className="nav-button " onClick={() => window.location.href = '/importar'}>Importar</button>
                    <button className="nav-button active " onClick={() => window.location.href = '/relatorio'}>Relatórios</button>
                </div>
                <div className="nav-right">
                    <img className="butao" src="imgs/settings_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="settings" />
                    <img className="butao" src="imgs/search_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png" alt="search" />
                    <img className="perfil butao" src="imgs/393b31d598db67553a2b00d95c6746ae43dfdc1f (1).png" alt="" />
                </div>
            </nav>

            <main className="conteudo">
                <section className="transacoes">
                    <h2>Transações</h2>
                    <div className="filtros">
                        <select>
                            <option>Últimos 15 dias</option>
                            <option>Últimos 30 dias</option>
                            <option>Maio</option>
                            <option>Abril</option>
                            <option>Março</option>
                            <option>Fevereiro</option>
                            <option>Janeiro</option>
                        </select>
                        <select>
                            <option>Todas Categorias</option>
                            <option>Lazer</option>
                            <option>Alimentação</option>
                            <option>Saúde</option>
                            <option>Outra</option>
                        </select>
                    </div>
                    <div className="box-dica">
                        As suas despesas estão sinalizadas em vermelho!
                    </div>
                    <table className="tabela-transacoes">
                        <thead>
                            <tr>
                                <th>Data</th>
                                <th>Categoria</th>
                                <th>Valor</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr><td>2023-09-25</td><td>Mercearia</td><td className="negativo">-R$20.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-09-22</td><td>Restaurante</td><td className="negativo">-R$45.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-09-21</td><td>Salário</td><td>R$1,000.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-09-18</td><td>Beleza</td><td className="negativo">-R$100.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-09-10</td><td>Eletrônicos</td><td className="negativo">-R$50.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-09-03</td><td>Empréstimo</td><td>R$500.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-08-30</td><td>Estorno</td><td>R$80.00</td><td><a href="#">Concluída</a></td></tr>
                            <tr><td>2023-08-28</td><td>Cartão</td><td className="negativo">-R$300.00</td><td><a href="#">Concluída</a></td></tr>
                        </tbody>
                    </table>
                </section>

                <section className="resumo">
                    <h2>Resumo</h2>
                    <div className="cards">
                        <div className="card">
                            <p>Renda Total</p>
                            <h3>R$1,580</h3>
                        </div>
                        <div className="card">
                            <p>Despesas totais</p>
                            <h3>(-)R$515,00</h3>
                        </div>
                        <div className="card">
                            <p>Saldo</p>
                            <h3>R$1,065</h3>
                        </div>
                    </div>
                </section>
            </main>
        </div>
    );
}