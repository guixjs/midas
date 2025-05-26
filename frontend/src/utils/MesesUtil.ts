export const gerarListaDeMeses = () => {
  const meses = [
    { nome: "Janeiro", valor: "01" },
    { nome: "Fevereiro", valor: "02" },
    { nome: "Março", valor: "03" },
    { nome: "Abril", valor: "04" },
    { nome: "Maio", valor: "05" },
    { nome: "Junho", valor: "06" },
    { nome: "Julho", valor: "07" },
    { nome: "Agosto", valor: "08" },
    { nome: "Setembro", valor: "09" },
    { nome: "Outubro", valor: "10" },
    { nome: "Novembro", valor: "11" },
    { nome: "Dezembro", valor: "12" },
  ];

  const anoAtual = new Date().getFullYear();
  const anos = [anoAtual, anoAtual - 1];

  const lista: { label: string; value: string }[] = [];

  anos.forEach((ano) => {
    meses.forEach((mes) => {
      lista.push({
        label: `${mes.nome}/${ano}`,
        value: `${ano}-${mes.valor}`,
      });
    });
  });

  return lista;
};


export const gerarMesAtual = ()=>{
  const data = new Date();

  const mes = data.toLocaleString('pt-BR', { month: 'long' }).replace(/^\w/, c => c.toUpperCase());
  const ano = data.getFullYear();
  const mesAno = `${mes}/${ano}`;

  return mesAno;
}