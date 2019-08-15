package com.tcs.product.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "id_produto")
    private Double idProduto;

    @Column(name = "cod_recipiente")
    private String codRecipiente;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "tp_embalagem")
    private String tpEmbalagem;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "id_dispositivo")
    private Double idDispositivo;

    @Column(name = "unidade_estoque")
    private String unidadeEstoque;

    @Column(name = "naladincca")
    private String naladincca;

    @Column(name = "ncm")
    private String ncm;

    @Column(name = "naladish")
    private String naladish;

    @Column(name = "linha_produto")
    private String linhaProduto;

    @Column(name = "peso_bruto")
    private Double pesoBruto;

    @Column(name = "peso_liquido")
    private Double pesoLiquido;

    @Column(name = "registro_ms")
    private String registroMs;

    @Column(name = "validade")
    private ZonedDateTime validade;

    @Column(name = "necessita_li")
    private String necessitaLi;

    @Column(name = "recof")
    private String recof;

    @Column(name = "reducao_icms")
    private Double reducaoIcms;

    @Column(name = "cod_onu")
    private String codOnu;

    @Column(name = "seq_suframa")
    private String seqSuframa;

    @Column(name = "nao_tributavel")
    private String naoTributavel;

    @Column(name = "ipi_especifico")
    private String ipiEspecifico;

    @Column(name = "ii_especifico")
    private String iiEspecifico;

    @Column(name = "ii")
    private Double ii;

    @Column(name = "ipi")
    private Double ipi;

    @Column(name = "valor_unitaria")
    private Double valorUnitaria;

    @Column(name = "capacidade_unitaria")
    private Double capacidadeUnitaria;

    @Column(name = "fator_conversao")
    private Double fatorConversao;

    @Column(name = "descricao_resumida")
    private String descricaoResumida;

    @Column(name = "atualizacao")
    private ZonedDateTime atualizacao;

    @Column(name = "status")
    private String status;

    @Column(name = "unidade_peso")
    private String unidadePeso;

    @Column(name = "cod_unidade_qtde")
    private String codUnidadeQtde;

    @Column(name = "cod_unidade_comercializada")
    private String codUnidadeComercializada;

    @Column(name = "cod_unidade_unitaria")
    private String codUnidadeUnitaria;

    @Column(name = "peso_quilo")
    private Double pesoQuilo;

    @Column(name = "peso_unid_comercializada")
    private Double pesoUnidComercializada;

    @Column(name = "cod_externo_gip")
    private String codExternoGip;

    @Column(name = "ultimo_informante")
    private Double ultimoInformante;

    @Column(name = "tsp")
    private String tsp;

    @Column(name = "tipo_recof")
    private String tipoRecof;

    @Column(name = "obs")
    private String obs;

    @Column(name = "peso_rateavel")
    private String pesoRateavel;

    @Column(name = "necessita_revisao")
    private String necessitaRevisao;

    @Column(name = "tipo_produto")
    private String tipoProduto;

    @Column(name = "procedencia")
    private String procedencia;

    @Column(name = "chassi")
    private String chassi;

    @Column(name = "especificacao_tecnica")
    private String especificacaoTecnica;

    @Column(name = "materia_prima_basica")
    private String materiaPrimaBasica;

    @Column(name = "automatico")
    private String automatico;

    @Column(name = "cod_origem")
    private String codOrigem;

    @Column(name = "material_generico")
    private String materialGenerico;

    @Column(name = "carga_perigosa")
    private String cargaPerigosa;

    @Column(name = "cod_unidade_venda")
    private String codUnidadeVenda;

    @Column(name = "flex_field_1")
    private String flexField1;

    @Column(name = "flex_field_2")
    private String flexField2;

    @Column(name = "flex_field_3")
    private String flexField3;

    @Column(name = "descricao_detalhada")
    private String descricaoDetalhada;

    @Column(name = "id_organizacao")
    private Double idOrganizacao;

    @Column(name = "cod_pais_origem")
    private String codPaisOrigem;

    @Column(name = "ciclo_produtivo")
    private Double cicloProdutivo;

    @Column(name = "part_number_fornecedor")
    private String partNumberFornecedor;

    @Column(name = "flag_atualiza_icms")
    private String flagAtualizaIcms;

    @Column(name = "id_dispositivo_ipi")
    private Double idDispositivoIpi;

    @Column(name = "codigo_moeda")
    private String codigoMoeda;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Column(name = "cod_prod")
    private String codProd;

    @Column(name = "cod_producao")
    private String codProducao;

    @Column(name = "procedencia_exp")
    private String procedenciaExp;

    @Column(name = "id_anuencia")
    private Double idAnuencia;

    @Column(name = "peso_metro_cubico")
    private Double pesoMetroCubico;

    @Column(name = "hts")
    private String hts;

    @Column(name = "nome_comercial")
    private String nomeComercial;

    @Column(name = "id_modelo")
    private Double idModelo;

    @Column(name = "unidade_fracionada")
    private String unidadeFracionada;

    @Column(name = "dif_peso_emb")
    private Double difPesoEmb;

    @Column(name = "class_prod_recof")
    private String classProdRecof;

    @Column(name = "data_inicio")
    private ZonedDateTime dataInicio;

    @Column(name = "data_fim")
    private ZonedDateTime dataFim;

    @Column(name = "data_insert_mov")
    private ZonedDateTime dataInsertMov;

    @Column(name = "id_corporativo")
    private String idCorporativo;

    @Column(name = "data_ger_leg")
    private ZonedDateTime dataGerLeg;

    @Column(name = "procedencia_info")
    private String procedenciaInfo;

    @Column(name = "cod_prod_suframa")
    private String codProdSuframa;

    @Column(name = "px_exp_tipoins")
    private String pxExpTipoins;

    @Column(name = "tipo_prod_suframa")
    private String tipoProdSuframa;

    @Column(name = "id_detalhe_suframa")
    private Double idDetalheSuframa;

    @Column(name = "valor_unitario_real")
    private Double valorUnitarioReal;

    @Column(name = "necessita_revisao_pexpam")
    private String necessitaRevisaoPexpam;

    @Column(name = "modelo")
    private Double modelo;

    @Column(name = "px_modelo_padrao")
    private Double pxModeloPadrao;

    @Column(name = "flex_field_4")
    private String flexField4;

    @Column(name = "flex_field_5")
    private String flexField5;

    @Column(name = "flex_field_6")
    private String flexField6;

    @Column(name = "flex_field_7")
    private String flexField7;

    @Column(name = "flex_field_8")
    private String flexField8;

    @Column(name = "flex_field_9")
    private String flexField9;

    @Column(name = "flex_field_10")
    private String flexField10;

    @Column(name = "flex_field_11")
    private String flexField11;

    @Column(name = "pis_cofins_tipo_aplic")
    private String pisCofinsTipoAplic;

    @Column(name = "pis")
    private Double pis;

    @Column(name = "cofins")
    private Double cofins;

    @Column(name = "pis_cofins_red_base")
    private Double pisCofinsRedBase;

    @Column(name = "modelo_prod_suframa")
    private String modeloProdSuframa;

    @Column(name = "cod_siscomex_unidade_ncm")
    private String codSiscomexUnidadeNcm;

    @Column(name = "part_number_cliente")
    private String partNumberCliente;

    @Column(name = "superficie_unitaria")
    private Double superficieUnitaria;

    @Column(name = "local_estoque")
    private String localEstoque;

    @Column(name = "cod_unidade_superficie")
    private String codUnidadeSuperficie;

    @Column(name = "rateio_produto_acabado")
    private String rateioProdutoAcabado;

    @Column(name = "pis_cofins_cod_un_espec")
    private String pisCofinsCodUnEspec;

    @Column(name = "recupera_impostos")
    private String recuperaImpostos;

    @Column(name = "flag_no_raf")
    private String flagNoRaf;

    @Column(name = "nota_compl_tipi")
    private String notaComplTipi;

    @Column(name = "ipi_reduzido")
    private Double ipiReduzido;

    @Column(name = "sujeito_lote")
    private String sujeitoLote;

    @Column(name = "marca_comercial")
    private String marcaComercial;

    @Column(name = "tipo_embalagem")
    private String tipoEmbalagem;

    @Column(name = "num_liberacao_brasilia")
    private String numLiberacaoBrasilia;

    @Column(name = "temperatura_conservacao")
    private String temperaturaConservacao;

    @Column(name = "umidade")
    private String umidade;

    @Column(name = "luminosidade")
    private String luminosidade;

    @Column(name = "embalagem_secundaria")
    private String embalagemSecundaria;

    @Column(name = "forma_fisica")
    private String formaFisica;

    @Column(name = "finalidade")
    private String finalidade;

    @Column(name = "item_produtivo_rc")
    private String itemProdutivoRc;

    @Column(name = "embalagem_primaria")
    private String embalagemPrimaria;

    @Column(name = "descricao_anvisa")
    private String descricaoAnvisa;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "cod_unidade_medida_dimensao")
    private String codUnidadeMedidaDimensao;

    @Column(name = "cod_material")
    private String codMaterial;

    @Column(name = "ativo")
    private String ativo;

    @Column(name = "codigo_aduana")
    private String codigoAduana;

    @Column(name = "classe_risco")
    private String classeRisco;

    @Column(name = "cod_risco")
    private String codRisco;

    @Column(name = "flex_field_12")
    private String flexField12;

    @Column(name = "flex_field_13")
    private String flexField13;

    @Column(name = "flex_field_1_number")
    private Double flexField1Number;

    @Column(name = "flex_field_2_number")
    private Double flexField2Number;

    @Column(name = "flex_field_3_number")
    private Double flexField3Number;

    @Column(name = "flex_field_4_number")
    private Double flexField4Number;

    @Column(name = "flex_field_5_number")
    private Double flexField5Number;

    @Column(name = "status_scansys")
    private String statusScansys;

    @Column(name = "cod_estrutura_atual")
    private Double codEstruturaAtual;

    @Column(name = "perc_tolerancia")
    private Integer percTolerancia;

    @Column(name = "pis_especifico")
    private String pisEspecifico;

    @Column(name = "cofins_especifico")
    private String cofinsEspecifico;

    @Column(name = "flex_field_14")
    private String flexField14;

    @Column(name = "flex_field_15")
    private String flexField15;

    @Column(name = "flex_field_16")
    private String flexField16;

    @Column(name = "flex_field_17")
    private String flexField17;

    @Column(name = "flex_field_18")
    private String flexField18;

    @Column(name = "flex_field_19")
    private String flexField19;

    @Column(name = "flex_field_20")
    private String flexField20;

    @Column(name = "flex_field_21")
    private String flexField21;

    @Column(name = "flex_field_22")
    private String flexField22;

    @Column(name = "flex_field_23")
    private String flexField23;

    @Column(name = "flex_field_24")
    private String flexField24;

    @Column(name = "flex_field_25")
    private String flexField25;

    @Column(name = "flex_field_26")
    private String flexField26;

    @Column(name = "flex_field_27")
    private String flexField27;

    @Column(name = "flex_field_28")
    private String flexField28;

    @Column(name = "flex_field_29")
    private String flexField29;

    @Column(name = "flex_field_30")
    private String flexField30;

    @Column(name = "flex_field_31")
    private String flexField31;

    @Column(name = "flex_field_32")
    private String flexField32;

    @Column(name = "flex_field_33")
    private String flexField33;

    @Column(name = "s_cod_barras_gtin")
    private String sCodBarrasGtin;

    @Column(name = "n_vlr_unit_limite_usd")
    private Double nVlrUnitLimiteUsd;

    @Column(name = "n_cod_prod_anp")
    private Double nCodProdAnp;

    @Column(name = "n_custo_producao")
    private Double nCustoProducao;

    @Column(name = "s_destino")
    private String sDestino;

    @Column(name = "n_percentual_glp")
    private Double nPercentualGlp;

    @Column(name = "n_loc_field_1")
    private Double nLocField1;

    @Column(name = "n_loc_field_2")
    private Double nLocField2;

    @Column(name = "n_loc_field_3")
    private Double nLocField3;

    @Column(name = "n_loc_field_4")
    private Double nLocField4;

    @Column(name = "n_loc_field_5")
    private Double nLocField5;

    @Column(name = "n_loc_field_6")
    private Double nLocField6;

    @Column(name = "n_loc_field_7")
    private Double nLocField7;

    @Column(name = "n_loc_field_8")
    private Double nLocField8;

    @Column(name = "s_loc_field_1")
    private String sLocField1;

    @Column(name = "s_loc_field_2")
    private String sLocField2;

    @Column(name = "s_loc_field_3")
    private String sLocField3;

    @Column(name = "s_loc_field_4")
    private String sLocField4;

    @Column(name = "s_loc_field_5")
    private String sLocField5;

    @Column(name = "n_id_doc_ocr")
    private Double nIdDocOcr;

    @Column(name = "s_loc_field_6")
    private String sLocField6;

    @Column(name = "s_loc_field_7")
    private String sLocField7;

    @Column(name = "s_loc_field_8")
    private String sLocField8;

    @Column(name = "s_loc_field_9")
    private String sLocField9;

    @Column(name = "s_loc_field_10")
    private String sLocField10;

    @Column(name = "s_loc_field_11")
    private String sLocField11;

    @Column(name = "s_loc_field_12")
    private String sLocField12;

    @Column(name = "s_loc_field_13")
    private String sLocField13;

    @Column(name = "s_loc_field_14")
    private String sLocField14;

    @Column(name = "s_loc_field_15")
    private String sLocField15;

    @Column(name = "s_cod_prod_anvisa")
    private String sCodProdAnvisa;

    @Column(name = "s_desc_prod_anp")
    private String sDescProdAnp;

    @Column(name = "n_perc_glp_nac")
    private Double nPercGlpNac;

    @Column(name = "n_perc_glp_imp")
    private Double nPercGlpImp;

    @Column(name = "n_valor_partida")
    private Double nValorPartida;

    @Column(name = "s_gtin_unid_trib")
    private String sGtinUnidTrib;

    @Column(name = "s_codigo_modalidade")
    private String sCodigoModalidade;

    @Column(name = "s_codigogpc")
    private String sCodigogpc;

    @Column(name = "s_codigogpcbrick")
    private String sCodigogpcbrick;

    @Column(name = "s_codigounspsc")
    private String sCodigounspsc;

    @Column(name = "s_situacao")
    private String sSituacao;

    @Column(name = "s_enviado")
    private String sEnviado;

    @Column(name = "s_motivo_isencao_anvisa")
    private String sMotivoIsencaoAnvisa;

    @Column(name = "s_ic_pronto_para_envio")
    private String sIcProntoParaEnvio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getIdProduto() {
        return idProduto;
    }

    public Product idProduto(Double idProduto) {
        this.idProduto = idProduto;
        return this;
    }

    public void setIdProduto(Double idProduto) {
        this.idProduto = idProduto;
    }

    public String getCodRecipiente() {
        return codRecipiente;
    }

    public Product codRecipiente(String codRecipiente) {
        this.codRecipiente = codRecipiente;
        return this;
    }

    public void setCodRecipiente(String codRecipiente) {
        this.codRecipiente = codRecipiente;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public Product partNumber(String partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getTpEmbalagem() {
        return tpEmbalagem;
    }

    public Product tpEmbalagem(String tpEmbalagem) {
        this.tpEmbalagem = tpEmbalagem;
        return this;
    }

    public void setTpEmbalagem(String tpEmbalagem) {
        this.tpEmbalagem = tpEmbalagem;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Product cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Double getIdDispositivo() {
        return idDispositivo;
    }

    public Product idDispositivo(Double idDispositivo) {
        this.idDispositivo = idDispositivo;
        return this;
    }

    public void setIdDispositivo(Double idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getUnidadeEstoque() {
        return unidadeEstoque;
    }

    public Product unidadeEstoque(String unidadeEstoque) {
        this.unidadeEstoque = unidadeEstoque;
        return this;
    }

    public void setUnidadeEstoque(String unidadeEstoque) {
        this.unidadeEstoque = unidadeEstoque;
    }

    public String getNaladincca() {
        return naladincca;
    }

    public Product naladincca(String naladincca) {
        this.naladincca = naladincca;
        return this;
    }

    public void setNaladincca(String naladincca) {
        this.naladincca = naladincca;
    }

    public String getNcm() {
        return ncm;
    }

    public Product ncm(String ncm) {
        this.ncm = ncm;
        return this;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getNaladish() {
        return naladish;
    }

    public Product naladish(String naladish) {
        this.naladish = naladish;
        return this;
    }

    public void setNaladish(String naladish) {
        this.naladish = naladish;
    }

    public String getLinhaProduto() {
        return linhaProduto;
    }

    public Product linhaProduto(String linhaProduto) {
        this.linhaProduto = linhaProduto;
        return this;
    }

    public void setLinhaProduto(String linhaProduto) {
        this.linhaProduto = linhaProduto;
    }

    public Double getPesoBruto() {
        return pesoBruto;
    }

    public Product pesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
        return this;
    }

    public void setPesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public Double getPesoLiquido() {
        return pesoLiquido;
    }

    public Product pesoLiquido(Double pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
        return this;
    }

    public void setPesoLiquido(Double pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    public String getRegistroMs() {
        return registroMs;
    }

    public Product registroMs(String registroMs) {
        this.registroMs = registroMs;
        return this;
    }

    public void setRegistroMs(String registroMs) {
        this.registroMs = registroMs;
    }

    public ZonedDateTime getValidade() {
        return validade;
    }

    public Product validade(ZonedDateTime validade) {
        this.validade = validade;
        return this;
    }

    public void setValidade(ZonedDateTime validade) {
        this.validade = validade;
    }

    public String getNecessitaLi() {
        return necessitaLi;
    }

    public Product necessitaLi(String necessitaLi) {
        this.necessitaLi = necessitaLi;
        return this;
    }

    public void setNecessitaLi(String necessitaLi) {
        this.necessitaLi = necessitaLi;
    }

    public String getRecof() {
        return recof;
    }

    public Product recof(String recof) {
        this.recof = recof;
        return this;
    }

    public void setRecof(String recof) {
        this.recof = recof;
    }

    public Double getReducaoIcms() {
        return reducaoIcms;
    }

    public Product reducaoIcms(Double reducaoIcms) {
        this.reducaoIcms = reducaoIcms;
        return this;
    }

    public void setReducaoIcms(Double reducaoIcms) {
        this.reducaoIcms = reducaoIcms;
    }

    public String getCodOnu() {
        return codOnu;
    }

    public Product codOnu(String codOnu) {
        this.codOnu = codOnu;
        return this;
    }

    public void setCodOnu(String codOnu) {
        this.codOnu = codOnu;
    }

    public String getSeqSuframa() {
        return seqSuframa;
    }

    public Product seqSuframa(String seqSuframa) {
        this.seqSuframa = seqSuframa;
        return this;
    }

    public void setSeqSuframa(String seqSuframa) {
        this.seqSuframa = seqSuframa;
    }

    public String getNaoTributavel() {
        return naoTributavel;
    }

    public Product naoTributavel(String naoTributavel) {
        this.naoTributavel = naoTributavel;
        return this;
    }

    public void setNaoTributavel(String naoTributavel) {
        this.naoTributavel = naoTributavel;
    }

    public String getIpiEspecifico() {
        return ipiEspecifico;
    }

    public Product ipiEspecifico(String ipiEspecifico) {
        this.ipiEspecifico = ipiEspecifico;
        return this;
    }

    public void setIpiEspecifico(String ipiEspecifico) {
        this.ipiEspecifico = ipiEspecifico;
    }

    public String getIiEspecifico() {
        return iiEspecifico;
    }

    public Product iiEspecifico(String iiEspecifico) {
        this.iiEspecifico = iiEspecifico;
        return this;
    }

    public void setIiEspecifico(String iiEspecifico) {
        this.iiEspecifico = iiEspecifico;
    }

    public Double getIi() {
        return ii;
    }

    public Product ii(Double ii) {
        this.ii = ii;
        return this;
    }

    public void setIi(Double ii) {
        this.ii = ii;
    }

    public Double getIpi() {
        return ipi;
    }

    public Product ipi(Double ipi) {
        this.ipi = ipi;
        return this;
    }

    public void setIpi(Double ipi) {
        this.ipi = ipi;
    }

    public Double getValorUnitaria() {
        return valorUnitaria;
    }

    public Product valorUnitaria(Double valorUnitaria) {
        this.valorUnitaria = valorUnitaria;
        return this;
    }

    public void setValorUnitaria(Double valorUnitaria) {
        this.valorUnitaria = valorUnitaria;
    }

    public Double getCapacidadeUnitaria() {
        return capacidadeUnitaria;
    }

    public Product capacidadeUnitaria(Double capacidadeUnitaria) {
        this.capacidadeUnitaria = capacidadeUnitaria;
        return this;
    }

    public void setCapacidadeUnitaria(Double capacidadeUnitaria) {
        this.capacidadeUnitaria = capacidadeUnitaria;
    }

    public Double getFatorConversao() {
        return fatorConversao;
    }

    public Product fatorConversao(Double fatorConversao) {
        this.fatorConversao = fatorConversao;
        return this;
    }

    public void setFatorConversao(Double fatorConversao) {
        this.fatorConversao = fatorConversao;
    }

    public String getDescricaoResumida() {
        return descricaoResumida;
    }

    public Product descricaoResumida(String descricaoResumida) {
        this.descricaoResumida = descricaoResumida;
        return this;
    }

    public void setDescricaoResumida(String descricaoResumida) {
        this.descricaoResumida = descricaoResumida;
    }

    public ZonedDateTime getAtualizacao() {
        return atualizacao;
    }

    public Product atualizacao(ZonedDateTime atualizacao) {
        this.atualizacao = atualizacao;
        return this;
    }

    public void setAtualizacao(ZonedDateTime atualizacao) {
        this.atualizacao = atualizacao;
    }

    public String getStatus() {
        return status;
    }

    public Product status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnidadePeso() {
        return unidadePeso;
    }

    public Product unidadePeso(String unidadePeso) {
        this.unidadePeso = unidadePeso;
        return this;
    }

    public void setUnidadePeso(String unidadePeso) {
        this.unidadePeso = unidadePeso;
    }

    public String getCodUnidadeQtde() {
        return codUnidadeQtde;
    }

    public Product codUnidadeQtde(String codUnidadeQtde) {
        this.codUnidadeQtde = codUnidadeQtde;
        return this;
    }

    public void setCodUnidadeQtde(String codUnidadeQtde) {
        this.codUnidadeQtde = codUnidadeQtde;
    }

    public String getCodUnidadeComercializada() {
        return codUnidadeComercializada;
    }

    public Product codUnidadeComercializada(String codUnidadeComercializada) {
        this.codUnidadeComercializada = codUnidadeComercializada;
        return this;
    }

    public void setCodUnidadeComercializada(String codUnidadeComercializada) {
        this.codUnidadeComercializada = codUnidadeComercializada;
    }

    public String getCodUnidadeUnitaria() {
        return codUnidadeUnitaria;
    }

    public Product codUnidadeUnitaria(String codUnidadeUnitaria) {
        this.codUnidadeUnitaria = codUnidadeUnitaria;
        return this;
    }

    public void setCodUnidadeUnitaria(String codUnidadeUnitaria) {
        this.codUnidadeUnitaria = codUnidadeUnitaria;
    }

    public Double getPesoQuilo() {
        return pesoQuilo;
    }

    public Product pesoQuilo(Double pesoQuilo) {
        this.pesoQuilo = pesoQuilo;
        return this;
    }

    public void setPesoQuilo(Double pesoQuilo) {
        this.pesoQuilo = pesoQuilo;
    }

    public Double getPesoUnidComercializada() {
        return pesoUnidComercializada;
    }

    public Product pesoUnidComercializada(Double pesoUnidComercializada) {
        this.pesoUnidComercializada = pesoUnidComercializada;
        return this;
    }

    public void setPesoUnidComercializada(Double pesoUnidComercializada) {
        this.pesoUnidComercializada = pesoUnidComercializada;
    }

    public String getCodExternoGip() {
        return codExternoGip;
    }

    public Product codExternoGip(String codExternoGip) {
        this.codExternoGip = codExternoGip;
        return this;
    }

    public void setCodExternoGip(String codExternoGip) {
        this.codExternoGip = codExternoGip;
    }

    public Double getUltimoInformante() {
        return ultimoInformante;
    }

    public Product ultimoInformante(Double ultimoInformante) {
        this.ultimoInformante = ultimoInformante;
        return this;
    }

    public void setUltimoInformante(Double ultimoInformante) {
        this.ultimoInformante = ultimoInformante;
    }

    public String getTsp() {
        return tsp;
    }

    public Product tsp(String tsp) {
        this.tsp = tsp;
        return this;
    }

    public void setTsp(String tsp) {
        this.tsp = tsp;
    }

    public String getTipoRecof() {
        return tipoRecof;
    }

    public Product tipoRecof(String tipoRecof) {
        this.tipoRecof = tipoRecof;
        return this;
    }

    public void setTipoRecof(String tipoRecof) {
        this.tipoRecof = tipoRecof;
    }

    public String getObs() {
        return obs;
    }

    public Product obs(String obs) {
        this.obs = obs;
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getPesoRateavel() {
        return pesoRateavel;
    }

    public Product pesoRateavel(String pesoRateavel) {
        this.pesoRateavel = pesoRateavel;
        return this;
    }

    public void setPesoRateavel(String pesoRateavel) {
        this.pesoRateavel = pesoRateavel;
    }

    public String getNecessitaRevisao() {
        return necessitaRevisao;
    }

    public Product necessitaRevisao(String necessitaRevisao) {
        this.necessitaRevisao = necessitaRevisao;
        return this;
    }

    public void setNecessitaRevisao(String necessitaRevisao) {
        this.necessitaRevisao = necessitaRevisao;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public Product tipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
        return this;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public Product procedencia(String procedencia) {
        this.procedencia = procedencia;
        return this;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getChassi() {
        return chassi;
    }

    public Product chassi(String chassi) {
        this.chassi = chassi;
        return this;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getEspecificacaoTecnica() {
        return especificacaoTecnica;
    }

    public Product especificacaoTecnica(String especificacaoTecnica) {
        this.especificacaoTecnica = especificacaoTecnica;
        return this;
    }

    public void setEspecificacaoTecnica(String especificacaoTecnica) {
        this.especificacaoTecnica = especificacaoTecnica;
    }

    public String getMateriaPrimaBasica() {
        return materiaPrimaBasica;
    }

    public Product materiaPrimaBasica(String materiaPrimaBasica) {
        this.materiaPrimaBasica = materiaPrimaBasica;
        return this;
    }

    public void setMateriaPrimaBasica(String materiaPrimaBasica) {
        this.materiaPrimaBasica = materiaPrimaBasica;
    }

    public String getAutomatico() {
        return automatico;
    }

    public Product automatico(String automatico) {
        this.automatico = automatico;
        return this;
    }

    public void setAutomatico(String automatico) {
        this.automatico = automatico;
    }

    public String getCodOrigem() {
        return codOrigem;
    }

    public Product codOrigem(String codOrigem) {
        this.codOrigem = codOrigem;
        return this;
    }

    public void setCodOrigem(String codOrigem) {
        this.codOrigem = codOrigem;
    }

    public String getMaterialGenerico() {
        return materialGenerico;
    }

    public Product materialGenerico(String materialGenerico) {
        this.materialGenerico = materialGenerico;
        return this;
    }

    public void setMaterialGenerico(String materialGenerico) {
        this.materialGenerico = materialGenerico;
    }

    public String getCargaPerigosa() {
        return cargaPerigosa;
    }

    public Product cargaPerigosa(String cargaPerigosa) {
        this.cargaPerigosa = cargaPerigosa;
        return this;
    }

    public void setCargaPerigosa(String cargaPerigosa) {
        this.cargaPerigosa = cargaPerigosa;
    }

    public String getCodUnidadeVenda() {
        return codUnidadeVenda;
    }

    public Product codUnidadeVenda(String codUnidadeVenda) {
        this.codUnidadeVenda = codUnidadeVenda;
        return this;
    }

    public void setCodUnidadeVenda(String codUnidadeVenda) {
        this.codUnidadeVenda = codUnidadeVenda;
    }

    public String getFlexField1() {
        return flexField1;
    }

    public Product flexField1(String flexField1) {
        this.flexField1 = flexField1;
        return this;
    }

    public void setFlexField1(String flexField1) {
        this.flexField1 = flexField1;
    }

    public String getFlexField2() {
        return flexField2;
    }

    public Product flexField2(String flexField2) {
        this.flexField2 = flexField2;
        return this;
    }

    public void setFlexField2(String flexField2) {
        this.flexField2 = flexField2;
    }

    public String getFlexField3() {
        return flexField3;
    }

    public Product flexField3(String flexField3) {
        this.flexField3 = flexField3;
        return this;
    }

    public void setFlexField3(String flexField3) {
        this.flexField3 = flexField3;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public Product descricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
        return this;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public Double getIdOrganizacao() {
        return idOrganizacao;
    }

    public Product idOrganizacao(Double idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
        return this;
    }

    public void setIdOrganizacao(Double idOrganizacao) {
        this.idOrganizacao = idOrganizacao;
    }

    public String getCodPaisOrigem() {
        return codPaisOrigem;
    }

    public Product codPaisOrigem(String codPaisOrigem) {
        this.codPaisOrigem = codPaisOrigem;
        return this;
    }

    public void setCodPaisOrigem(String codPaisOrigem) {
        this.codPaisOrigem = codPaisOrigem;
    }

    public Double getCicloProdutivo() {
        return cicloProdutivo;
    }

    public Product cicloProdutivo(Double cicloProdutivo) {
        this.cicloProdutivo = cicloProdutivo;
        return this;
    }

    public void setCicloProdutivo(Double cicloProdutivo) {
        this.cicloProdutivo = cicloProdutivo;
    }

    public String getPartNumberFornecedor() {
        return partNumberFornecedor;
    }

    public Product partNumberFornecedor(String partNumberFornecedor) {
        this.partNumberFornecedor = partNumberFornecedor;
        return this;
    }

    public void setPartNumberFornecedor(String partNumberFornecedor) {
        this.partNumberFornecedor = partNumberFornecedor;
    }

    public String getFlagAtualizaIcms() {
        return flagAtualizaIcms;
    }

    public Product flagAtualizaIcms(String flagAtualizaIcms) {
        this.flagAtualizaIcms = flagAtualizaIcms;
        return this;
    }

    public void setFlagAtualizaIcms(String flagAtualizaIcms) {
        this.flagAtualizaIcms = flagAtualizaIcms;
    }

    public Double getIdDispositivoIpi() {
        return idDispositivoIpi;
    }

    public Product idDispositivoIpi(Double idDispositivoIpi) {
        this.idDispositivoIpi = idDispositivoIpi;
        return this;
    }

    public void setIdDispositivoIpi(Double idDispositivoIpi) {
        this.idDispositivoIpi = idDispositivoIpi;
    }

    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    public Product codigoMoeda(String codigoMoeda) {
        this.codigoMoeda = codigoMoeda;
        return this;
    }

    public void setCodigoMoeda(String codigoMoeda) {
        this.codigoMoeda = codigoMoeda;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public Product valorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getCodProd() {
        return codProd;
    }

    public Product codProd(String codProd) {
        this.codProd = codProd;
        return this;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getCodProducao() {
        return codProducao;
    }

    public Product codProducao(String codProducao) {
        this.codProducao = codProducao;
        return this;
    }

    public void setCodProducao(String codProducao) {
        this.codProducao = codProducao;
    }

    public String getProcedenciaExp() {
        return procedenciaExp;
    }

    public Product procedenciaExp(String procedenciaExp) {
        this.procedenciaExp = procedenciaExp;
        return this;
    }

    public void setProcedenciaExp(String procedenciaExp) {
        this.procedenciaExp = procedenciaExp;
    }

    public Double getIdAnuencia() {
        return idAnuencia;
    }

    public Product idAnuencia(Double idAnuencia) {
        this.idAnuencia = idAnuencia;
        return this;
    }

    public void setIdAnuencia(Double idAnuencia) {
        this.idAnuencia = idAnuencia;
    }

    public Double getPesoMetroCubico() {
        return pesoMetroCubico;
    }

    public Product pesoMetroCubico(Double pesoMetroCubico) {
        this.pesoMetroCubico = pesoMetroCubico;
        return this;
    }

    public void setPesoMetroCubico(Double pesoMetroCubico) {
        this.pesoMetroCubico = pesoMetroCubico;
    }

    public String getHts() {
        return hts;
    }

    public Product hts(String hts) {
        this.hts = hts;
        return this;
    }

    public void setHts(String hts) {
        this.hts = hts;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public Product nomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
        return this;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public Double getIdModelo() {
        return idModelo;
    }

    public Product idModelo(Double idModelo) {
        this.idModelo = idModelo;
        return this;
    }

    public void setIdModelo(Double idModelo) {
        this.idModelo = idModelo;
    }

    public String getUnidadeFracionada() {
        return unidadeFracionada;
    }

    public Product unidadeFracionada(String unidadeFracionada) {
        this.unidadeFracionada = unidadeFracionada;
        return this;
    }

    public void setUnidadeFracionada(String unidadeFracionada) {
        this.unidadeFracionada = unidadeFracionada;
    }

    public Double getDifPesoEmb() {
        return difPesoEmb;
    }

    public Product difPesoEmb(Double difPesoEmb) {
        this.difPesoEmb = difPesoEmb;
        return this;
    }

    public void setDifPesoEmb(Double difPesoEmb) {
        this.difPesoEmb = difPesoEmb;
    }

    public String getClassProdRecof() {
        return classProdRecof;
    }

    public Product classProdRecof(String classProdRecof) {
        this.classProdRecof = classProdRecof;
        return this;
    }

    public void setClassProdRecof(String classProdRecof) {
        this.classProdRecof = classProdRecof;
    }

    public ZonedDateTime getDataInicio() {
        return dataInicio;
    }

    public Product dataInicio(ZonedDateTime dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(ZonedDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public ZonedDateTime getDataFim() {
        return dataFim;
    }

    public Product dataFim(ZonedDateTime dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(ZonedDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public ZonedDateTime getDataInsertMov() {
        return dataInsertMov;
    }

    public Product dataInsertMov(ZonedDateTime dataInsertMov) {
        this.dataInsertMov = dataInsertMov;
        return this;
    }

    public void setDataInsertMov(ZonedDateTime dataInsertMov) {
        this.dataInsertMov = dataInsertMov;
    }

    public String getIdCorporativo() {
        return idCorporativo;
    }

    public Product idCorporativo(String idCorporativo) {
        this.idCorporativo = idCorporativo;
        return this;
    }

    public void setIdCorporativo(String idCorporativo) {
        this.idCorporativo = idCorporativo;
    }

    public ZonedDateTime getDataGerLeg() {
        return dataGerLeg;
    }

    public Product dataGerLeg(ZonedDateTime dataGerLeg) {
        this.dataGerLeg = dataGerLeg;
        return this;
    }

    public void setDataGerLeg(ZonedDateTime dataGerLeg) {
        this.dataGerLeg = dataGerLeg;
    }

    public String getProcedenciaInfo() {
        return procedenciaInfo;
    }

    public Product procedenciaInfo(String procedenciaInfo) {
        this.procedenciaInfo = procedenciaInfo;
        return this;
    }

    public void setProcedenciaInfo(String procedenciaInfo) {
        this.procedenciaInfo = procedenciaInfo;
    }

    public String getCodProdSuframa() {
        return codProdSuframa;
    }

    public Product codProdSuframa(String codProdSuframa) {
        this.codProdSuframa = codProdSuframa;
        return this;
    }

    public void setCodProdSuframa(String codProdSuframa) {
        this.codProdSuframa = codProdSuframa;
    }

    public String getPxExpTipoins() {
        return pxExpTipoins;
    }

    public Product pxExpTipoins(String pxExpTipoins) {
        this.pxExpTipoins = pxExpTipoins;
        return this;
    }

    public void setPxExpTipoins(String pxExpTipoins) {
        this.pxExpTipoins = pxExpTipoins;
    }

    public String getTipoProdSuframa() {
        return tipoProdSuframa;
    }

    public Product tipoProdSuframa(String tipoProdSuframa) {
        this.tipoProdSuframa = tipoProdSuframa;
        return this;
    }

    public void setTipoProdSuframa(String tipoProdSuframa) {
        this.tipoProdSuframa = tipoProdSuframa;
    }

    public Double getIdDetalheSuframa() {
        return idDetalheSuframa;
    }

    public Product idDetalheSuframa(Double idDetalheSuframa) {
        this.idDetalheSuframa = idDetalheSuframa;
        return this;
    }

    public void setIdDetalheSuframa(Double idDetalheSuframa) {
        this.idDetalheSuframa = idDetalheSuframa;
    }

    public Double getValorUnitarioReal() {
        return valorUnitarioReal;
    }

    public Product valorUnitarioReal(Double valorUnitarioReal) {
        this.valorUnitarioReal = valorUnitarioReal;
        return this;
    }

    public void setValorUnitarioReal(Double valorUnitarioReal) {
        this.valorUnitarioReal = valorUnitarioReal;
    }

    public String getNecessitaRevisaoPexpam() {
        return necessitaRevisaoPexpam;
    }

    public Product necessitaRevisaoPexpam(String necessitaRevisaoPexpam) {
        this.necessitaRevisaoPexpam = necessitaRevisaoPexpam;
        return this;
    }

    public void setNecessitaRevisaoPexpam(String necessitaRevisaoPexpam) {
        this.necessitaRevisaoPexpam = necessitaRevisaoPexpam;
    }

    public Double getModelo() {
        return modelo;
    }

    public Product modelo(Double modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(Double modelo) {
        this.modelo = modelo;
    }

    public Double getPxModeloPadrao() {
        return pxModeloPadrao;
    }

    public Product pxModeloPadrao(Double pxModeloPadrao) {
        this.pxModeloPadrao = pxModeloPadrao;
        return this;
    }

    public void setPxModeloPadrao(Double pxModeloPadrao) {
        this.pxModeloPadrao = pxModeloPadrao;
    }

    public String getFlexField4() {
        return flexField4;
    }

    public Product flexField4(String flexField4) {
        this.flexField4 = flexField4;
        return this;
    }

    public void setFlexField4(String flexField4) {
        this.flexField4 = flexField4;
    }

    public String getFlexField5() {
        return flexField5;
    }

    public Product flexField5(String flexField5) {
        this.flexField5 = flexField5;
        return this;
    }

    public void setFlexField5(String flexField5) {
        this.flexField5 = flexField5;
    }

    public String getFlexField6() {
        return flexField6;
    }

    public Product flexField6(String flexField6) {
        this.flexField6 = flexField6;
        return this;
    }

    public void setFlexField6(String flexField6) {
        this.flexField6 = flexField6;
    }

    public String getFlexField7() {
        return flexField7;
    }

    public Product flexField7(String flexField7) {
        this.flexField7 = flexField7;
        return this;
    }

    public void setFlexField7(String flexField7) {
        this.flexField7 = flexField7;
    }

    public String getFlexField8() {
        return flexField8;
    }

    public Product flexField8(String flexField8) {
        this.flexField8 = flexField8;
        return this;
    }

    public void setFlexField8(String flexField8) {
        this.flexField8 = flexField8;
    }

    public String getFlexField9() {
        return flexField9;
    }

    public Product flexField9(String flexField9) {
        this.flexField9 = flexField9;
        return this;
    }

    public void setFlexField9(String flexField9) {
        this.flexField9 = flexField9;
    }

    public String getFlexField10() {
        return flexField10;
    }

    public Product flexField10(String flexField10) {
        this.flexField10 = flexField10;
        return this;
    }

    public void setFlexField10(String flexField10) {
        this.flexField10 = flexField10;
    }

    public String getFlexField11() {
        return flexField11;
    }

    public Product flexField11(String flexField11) {
        this.flexField11 = flexField11;
        return this;
    }

    public void setFlexField11(String flexField11) {
        this.flexField11 = flexField11;
    }

    public String getPisCofinsTipoAplic() {
        return pisCofinsTipoAplic;
    }

    public Product pisCofinsTipoAplic(String pisCofinsTipoAplic) {
        this.pisCofinsTipoAplic = pisCofinsTipoAplic;
        return this;
    }

    public void setPisCofinsTipoAplic(String pisCofinsTipoAplic) {
        this.pisCofinsTipoAplic = pisCofinsTipoAplic;
    }

    public Double getPis() {
        return pis;
    }

    public Product pis(Double pis) {
        this.pis = pis;
        return this;
    }

    public void setPis(Double pis) {
        this.pis = pis;
    }

    public Double getCofins() {
        return cofins;
    }

    public Product cofins(Double cofins) {
        this.cofins = cofins;
        return this;
    }

    public void setCofins(Double cofins) {
        this.cofins = cofins;
    }

    public Double getPisCofinsRedBase() {
        return pisCofinsRedBase;
    }

    public Product pisCofinsRedBase(Double pisCofinsRedBase) {
        this.pisCofinsRedBase = pisCofinsRedBase;
        return this;
    }

    public void setPisCofinsRedBase(Double pisCofinsRedBase) {
        this.pisCofinsRedBase = pisCofinsRedBase;
    }

    public String getModeloProdSuframa() {
        return modeloProdSuframa;
    }

    public Product modeloProdSuframa(String modeloProdSuframa) {
        this.modeloProdSuframa = modeloProdSuframa;
        return this;
    }

    public void setModeloProdSuframa(String modeloProdSuframa) {
        this.modeloProdSuframa = modeloProdSuframa;
    }

    public String getCodSiscomexUnidadeNcm() {
        return codSiscomexUnidadeNcm;
    }

    public Product codSiscomexUnidadeNcm(String codSiscomexUnidadeNcm) {
        this.codSiscomexUnidadeNcm = codSiscomexUnidadeNcm;
        return this;
    }

    public void setCodSiscomexUnidadeNcm(String codSiscomexUnidadeNcm) {
        this.codSiscomexUnidadeNcm = codSiscomexUnidadeNcm;
    }

    public String getPartNumberCliente() {
        return partNumberCliente;
    }

    public Product partNumberCliente(String partNumberCliente) {
        this.partNumberCliente = partNumberCliente;
        return this;
    }

    public void setPartNumberCliente(String partNumberCliente) {
        this.partNumberCliente = partNumberCliente;
    }

    public Double getSuperficieUnitaria() {
        return superficieUnitaria;
    }

    public Product superficieUnitaria(Double superficieUnitaria) {
        this.superficieUnitaria = superficieUnitaria;
        return this;
    }

    public void setSuperficieUnitaria(Double superficieUnitaria) {
        this.superficieUnitaria = superficieUnitaria;
    }

    public String getLocalEstoque() {
        return localEstoque;
    }

    public Product localEstoque(String localEstoque) {
        this.localEstoque = localEstoque;
        return this;
    }

    public void setLocalEstoque(String localEstoque) {
        this.localEstoque = localEstoque;
    }

    public String getCodUnidadeSuperficie() {
        return codUnidadeSuperficie;
    }

    public Product codUnidadeSuperficie(String codUnidadeSuperficie) {
        this.codUnidadeSuperficie = codUnidadeSuperficie;
        return this;
    }

    public void setCodUnidadeSuperficie(String codUnidadeSuperficie) {
        this.codUnidadeSuperficie = codUnidadeSuperficie;
    }

    public String getRateioProdutoAcabado() {
        return rateioProdutoAcabado;
    }

    public Product rateioProdutoAcabado(String rateioProdutoAcabado) {
        this.rateioProdutoAcabado = rateioProdutoAcabado;
        return this;
    }

    public void setRateioProdutoAcabado(String rateioProdutoAcabado) {
        this.rateioProdutoAcabado = rateioProdutoAcabado;
    }

    public String getPisCofinsCodUnEspec() {
        return pisCofinsCodUnEspec;
    }

    public Product pisCofinsCodUnEspec(String pisCofinsCodUnEspec) {
        this.pisCofinsCodUnEspec = pisCofinsCodUnEspec;
        return this;
    }

    public void setPisCofinsCodUnEspec(String pisCofinsCodUnEspec) {
        this.pisCofinsCodUnEspec = pisCofinsCodUnEspec;
    }

    public String getRecuperaImpostos() {
        return recuperaImpostos;
    }

    public Product recuperaImpostos(String recuperaImpostos) {
        this.recuperaImpostos = recuperaImpostos;
        return this;
    }

    public void setRecuperaImpostos(String recuperaImpostos) {
        this.recuperaImpostos = recuperaImpostos;
    }

    public String getFlagNoRaf() {
        return flagNoRaf;
    }

    public Product flagNoRaf(String flagNoRaf) {
        this.flagNoRaf = flagNoRaf;
        return this;
    }

    public void setFlagNoRaf(String flagNoRaf) {
        this.flagNoRaf = flagNoRaf;
    }

    public String getNotaComplTipi() {
        return notaComplTipi;
    }

    public Product notaComplTipi(String notaComplTipi) {
        this.notaComplTipi = notaComplTipi;
        return this;
    }

    public void setNotaComplTipi(String notaComplTipi) {
        this.notaComplTipi = notaComplTipi;
    }

    public Double getIpiReduzido() {
        return ipiReduzido;
    }

    public Product ipiReduzido(Double ipiReduzido) {
        this.ipiReduzido = ipiReduzido;
        return this;
    }

    public void setIpiReduzido(Double ipiReduzido) {
        this.ipiReduzido = ipiReduzido;
    }

    public String getSujeitoLote() {
        return sujeitoLote;
    }

    public Product sujeitoLote(String sujeitoLote) {
        this.sujeitoLote = sujeitoLote;
        return this;
    }

    public void setSujeitoLote(String sujeitoLote) {
        this.sujeitoLote = sujeitoLote;
    }

    public String getMarcaComercial() {
        return marcaComercial;
    }

    public Product marcaComercial(String marcaComercial) {
        this.marcaComercial = marcaComercial;
        return this;
    }

    public void setMarcaComercial(String marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public String getTipoEmbalagem() {
        return tipoEmbalagem;
    }

    public Product tipoEmbalagem(String tipoEmbalagem) {
        this.tipoEmbalagem = tipoEmbalagem;
        return this;
    }

    public void setTipoEmbalagem(String tipoEmbalagem) {
        this.tipoEmbalagem = tipoEmbalagem;
    }

    public String getNumLiberacaoBrasilia() {
        return numLiberacaoBrasilia;
    }

    public Product numLiberacaoBrasilia(String numLiberacaoBrasilia) {
        this.numLiberacaoBrasilia = numLiberacaoBrasilia;
        return this;
    }

    public void setNumLiberacaoBrasilia(String numLiberacaoBrasilia) {
        this.numLiberacaoBrasilia = numLiberacaoBrasilia;
    }

    public String getTemperaturaConservacao() {
        return temperaturaConservacao;
    }

    public Product temperaturaConservacao(String temperaturaConservacao) {
        this.temperaturaConservacao = temperaturaConservacao;
        return this;
    }

    public void setTemperaturaConservacao(String temperaturaConservacao) {
        this.temperaturaConservacao = temperaturaConservacao;
    }

    public String getUmidade() {
        return umidade;
    }

    public Product umidade(String umidade) {
        this.umidade = umidade;
        return this;
    }

    public void setUmidade(String umidade) {
        this.umidade = umidade;
    }

    public String getLuminosidade() {
        return luminosidade;
    }

    public Product luminosidade(String luminosidade) {
        this.luminosidade = luminosidade;
        return this;
    }

    public void setLuminosidade(String luminosidade) {
        this.luminosidade = luminosidade;
    }

    public String getEmbalagemSecundaria() {
        return embalagemSecundaria;
    }

    public Product embalagemSecundaria(String embalagemSecundaria) {
        this.embalagemSecundaria = embalagemSecundaria;
        return this;
    }

    public void setEmbalagemSecundaria(String embalagemSecundaria) {
        this.embalagemSecundaria = embalagemSecundaria;
    }

    public String getFormaFisica() {
        return formaFisica;
    }

    public Product formaFisica(String formaFisica) {
        this.formaFisica = formaFisica;
        return this;
    }

    public void setFormaFisica(String formaFisica) {
        this.formaFisica = formaFisica;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public Product finalidade(String finalidade) {
        this.finalidade = finalidade;
        return this;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public String getItemProdutivoRc() {
        return itemProdutivoRc;
    }

    public Product itemProdutivoRc(String itemProdutivoRc) {
        this.itemProdutivoRc = itemProdutivoRc;
        return this;
    }

    public void setItemProdutivoRc(String itemProdutivoRc) {
        this.itemProdutivoRc = itemProdutivoRc;
    }

    public String getEmbalagemPrimaria() {
        return embalagemPrimaria;
    }

    public Product embalagemPrimaria(String embalagemPrimaria) {
        this.embalagemPrimaria = embalagemPrimaria;
        return this;
    }

    public void setEmbalagemPrimaria(String embalagemPrimaria) {
        this.embalagemPrimaria = embalagemPrimaria;
    }

    public String getDescricaoAnvisa() {
        return descricaoAnvisa;
    }

    public Product descricaoAnvisa(String descricaoAnvisa) {
        this.descricaoAnvisa = descricaoAnvisa;
        return this;
    }

    public void setDescricaoAnvisa(String descricaoAnvisa) {
        this.descricaoAnvisa = descricaoAnvisa;
    }

    public Double getVolume() {
        return volume;
    }

    public Product volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getCodUnidadeMedidaDimensao() {
        return codUnidadeMedidaDimensao;
    }

    public Product codUnidadeMedidaDimensao(String codUnidadeMedidaDimensao) {
        this.codUnidadeMedidaDimensao = codUnidadeMedidaDimensao;
        return this;
    }

    public void setCodUnidadeMedidaDimensao(String codUnidadeMedidaDimensao) {
        this.codUnidadeMedidaDimensao = codUnidadeMedidaDimensao;
    }

    public String getCodMaterial() {
        return codMaterial;
    }

    public Product codMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
        return this;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getAtivo() {
        return ativo;
    }

    public Product ativo(String ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getCodigoAduana() {
        return codigoAduana;
    }

    public Product codigoAduana(String codigoAduana) {
        this.codigoAduana = codigoAduana;
        return this;
    }

    public void setCodigoAduana(String codigoAduana) {
        this.codigoAduana = codigoAduana;
    }

    public String getClasseRisco() {
        return classeRisco;
    }

    public Product classeRisco(String classeRisco) {
        this.classeRisco = classeRisco;
        return this;
    }

    public void setClasseRisco(String classeRisco) {
        this.classeRisco = classeRisco;
    }

    public String getCodRisco() {
        return codRisco;
    }

    public Product codRisco(String codRisco) {
        this.codRisco = codRisco;
        return this;
    }

    public void setCodRisco(String codRisco) {
        this.codRisco = codRisco;
    }

    public String getFlexField12() {
        return flexField12;
    }

    public Product flexField12(String flexField12) {
        this.flexField12 = flexField12;
        return this;
    }

    public void setFlexField12(String flexField12) {
        this.flexField12 = flexField12;
    }

    public String getFlexField13() {
        return flexField13;
    }

    public Product flexField13(String flexField13) {
        this.flexField13 = flexField13;
        return this;
    }

    public void setFlexField13(String flexField13) {
        this.flexField13 = flexField13;
    }

    public Double getFlexField1Number() {
        return flexField1Number;
    }

    public Product flexField1Number(Double flexField1Number) {
        this.flexField1Number = flexField1Number;
        return this;
    }

    public void setFlexField1Number(Double flexField1Number) {
        this.flexField1Number = flexField1Number;
    }

    public Double getFlexField2Number() {
        return flexField2Number;
    }

    public Product flexField2Number(Double flexField2Number) {
        this.flexField2Number = flexField2Number;
        return this;
    }

    public void setFlexField2Number(Double flexField2Number) {
        this.flexField2Number = flexField2Number;
    }

    public Double getFlexField3Number() {
        return flexField3Number;
    }

    public Product flexField3Number(Double flexField3Number) {
        this.flexField3Number = flexField3Number;
        return this;
    }

    public void setFlexField3Number(Double flexField3Number) {
        this.flexField3Number = flexField3Number;
    }

    public Double getFlexField4Number() {
        return flexField4Number;
    }

    public Product flexField4Number(Double flexField4Number) {
        this.flexField4Number = flexField4Number;
        return this;
    }

    public void setFlexField4Number(Double flexField4Number) {
        this.flexField4Number = flexField4Number;
    }

    public Double getFlexField5Number() {
        return flexField5Number;
    }

    public Product flexField5Number(Double flexField5Number) {
        this.flexField5Number = flexField5Number;
        return this;
    }

    public void setFlexField5Number(Double flexField5Number) {
        this.flexField5Number = flexField5Number;
    }

    public String getStatusScansys() {
        return statusScansys;
    }

    public Product statusScansys(String statusScansys) {
        this.statusScansys = statusScansys;
        return this;
    }

    public void setStatusScansys(String statusScansys) {
        this.statusScansys = statusScansys;
    }

    public Double getCodEstruturaAtual() {
        return codEstruturaAtual;
    }

    public Product codEstruturaAtual(Double codEstruturaAtual) {
        this.codEstruturaAtual = codEstruturaAtual;
        return this;
    }

    public void setCodEstruturaAtual(Double codEstruturaAtual) {
        this.codEstruturaAtual = codEstruturaAtual;
    }

    public Integer getPercTolerancia() {
        return percTolerancia;
    }

    public Product percTolerancia(Integer percTolerancia) {
        this.percTolerancia = percTolerancia;
        return this;
    }

    public void setPercTolerancia(Integer percTolerancia) {
        this.percTolerancia = percTolerancia;
    }

    public String getPisEspecifico() {
        return pisEspecifico;
    }

    public Product pisEspecifico(String pisEspecifico) {
        this.pisEspecifico = pisEspecifico;
        return this;
    }

    public void setPisEspecifico(String pisEspecifico) {
        this.pisEspecifico = pisEspecifico;
    }

    public String getCofinsEspecifico() {
        return cofinsEspecifico;
    }

    public Product cofinsEspecifico(String cofinsEspecifico) {
        this.cofinsEspecifico = cofinsEspecifico;
        return this;
    }

    public void setCofinsEspecifico(String cofinsEspecifico) {
        this.cofinsEspecifico = cofinsEspecifico;
    }

    public String getFlexField14() {
        return flexField14;
    }

    public Product flexField14(String flexField14) {
        this.flexField14 = flexField14;
        return this;
    }

    public void setFlexField14(String flexField14) {
        this.flexField14 = flexField14;
    }

    public String getFlexField15() {
        return flexField15;
    }

    public Product flexField15(String flexField15) {
        this.flexField15 = flexField15;
        return this;
    }

    public void setFlexField15(String flexField15) {
        this.flexField15 = flexField15;
    }

    public String getFlexField16() {
        return flexField16;
    }

    public Product flexField16(String flexField16) {
        this.flexField16 = flexField16;
        return this;
    }

    public void setFlexField16(String flexField16) {
        this.flexField16 = flexField16;
    }

    public String getFlexField17() {
        return flexField17;
    }

    public Product flexField17(String flexField17) {
        this.flexField17 = flexField17;
        return this;
    }

    public void setFlexField17(String flexField17) {
        this.flexField17 = flexField17;
    }

    public String getFlexField18() {
        return flexField18;
    }

    public Product flexField18(String flexField18) {
        this.flexField18 = flexField18;
        return this;
    }

    public void setFlexField18(String flexField18) {
        this.flexField18 = flexField18;
    }

    public String getFlexField19() {
        return flexField19;
    }

    public Product flexField19(String flexField19) {
        this.flexField19 = flexField19;
        return this;
    }

    public void setFlexField19(String flexField19) {
        this.flexField19 = flexField19;
    }

    public String getFlexField20() {
        return flexField20;
    }

    public Product flexField20(String flexField20) {
        this.flexField20 = flexField20;
        return this;
    }

    public void setFlexField20(String flexField20) {
        this.flexField20 = flexField20;
    }

    public String getFlexField21() {
        return flexField21;
    }

    public Product flexField21(String flexField21) {
        this.flexField21 = flexField21;
        return this;
    }

    public void setFlexField21(String flexField21) {
        this.flexField21 = flexField21;
    }

    public String getFlexField22() {
        return flexField22;
    }

    public Product flexField22(String flexField22) {
        this.flexField22 = flexField22;
        return this;
    }

    public void setFlexField22(String flexField22) {
        this.flexField22 = flexField22;
    }

    public String getFlexField23() {
        return flexField23;
    }

    public Product flexField23(String flexField23) {
        this.flexField23 = flexField23;
        return this;
    }

    public void setFlexField23(String flexField23) {
        this.flexField23 = flexField23;
    }

    public String getFlexField24() {
        return flexField24;
    }

    public Product flexField24(String flexField24) {
        this.flexField24 = flexField24;
        return this;
    }

    public void setFlexField24(String flexField24) {
        this.flexField24 = flexField24;
    }

    public String getFlexField25() {
        return flexField25;
    }

    public Product flexField25(String flexField25) {
        this.flexField25 = flexField25;
        return this;
    }

    public void setFlexField25(String flexField25) {
        this.flexField25 = flexField25;
    }

    public String getFlexField26() {
        return flexField26;
    }

    public Product flexField26(String flexField26) {
        this.flexField26 = flexField26;
        return this;
    }

    public void setFlexField26(String flexField26) {
        this.flexField26 = flexField26;
    }

    public String getFlexField27() {
        return flexField27;
    }

    public Product flexField27(String flexField27) {
        this.flexField27 = flexField27;
        return this;
    }

    public void setFlexField27(String flexField27) {
        this.flexField27 = flexField27;
    }

    public String getFlexField28() {
        return flexField28;
    }

    public Product flexField28(String flexField28) {
        this.flexField28 = flexField28;
        return this;
    }

    public void setFlexField28(String flexField28) {
        this.flexField28 = flexField28;
    }

    public String getFlexField29() {
        return flexField29;
    }

    public Product flexField29(String flexField29) {
        this.flexField29 = flexField29;
        return this;
    }

    public void setFlexField29(String flexField29) {
        this.flexField29 = flexField29;
    }

    public String getFlexField30() {
        return flexField30;
    }

    public Product flexField30(String flexField30) {
        this.flexField30 = flexField30;
        return this;
    }

    public void setFlexField30(String flexField30) {
        this.flexField30 = flexField30;
    }

    public String getFlexField31() {
        return flexField31;
    }

    public Product flexField31(String flexField31) {
        this.flexField31 = flexField31;
        return this;
    }

    public void setFlexField31(String flexField31) {
        this.flexField31 = flexField31;
    }

    public String getFlexField32() {
        return flexField32;
    }

    public Product flexField32(String flexField32) {
        this.flexField32 = flexField32;
        return this;
    }

    public void setFlexField32(String flexField32) {
        this.flexField32 = flexField32;
    }

    public String getFlexField33() {
        return flexField33;
    }

    public Product flexField33(String flexField33) {
        this.flexField33 = flexField33;
        return this;
    }

    public void setFlexField33(String flexField33) {
        this.flexField33 = flexField33;
    }

    public String getsCodBarrasGtin() {
        return sCodBarrasGtin;
    }

    public Product sCodBarrasGtin(String sCodBarrasGtin) {
        this.sCodBarrasGtin = sCodBarrasGtin;
        return this;
    }

    public void setsCodBarrasGtin(String sCodBarrasGtin) {
        this.sCodBarrasGtin = sCodBarrasGtin;
    }

    public Double getnVlrUnitLimiteUsd() {
        return nVlrUnitLimiteUsd;
    }

    public Product nVlrUnitLimiteUsd(Double nVlrUnitLimiteUsd) {
        this.nVlrUnitLimiteUsd = nVlrUnitLimiteUsd;
        return this;
    }

    public void setnVlrUnitLimiteUsd(Double nVlrUnitLimiteUsd) {
        this.nVlrUnitLimiteUsd = nVlrUnitLimiteUsd;
    }

    public Double getnCodProdAnp() {
        return nCodProdAnp;
    }

    public Product nCodProdAnp(Double nCodProdAnp) {
        this.nCodProdAnp = nCodProdAnp;
        return this;
    }

    public void setnCodProdAnp(Double nCodProdAnp) {
        this.nCodProdAnp = nCodProdAnp;
    }

    public Double getnCustoProducao() {
        return nCustoProducao;
    }

    public Product nCustoProducao(Double nCustoProducao) {
        this.nCustoProducao = nCustoProducao;
        return this;
    }

    public void setnCustoProducao(Double nCustoProducao) {
        this.nCustoProducao = nCustoProducao;
    }

    public String getsDestino() {
        return sDestino;
    }

    public Product sDestino(String sDestino) {
        this.sDestino = sDestino;
        return this;
    }

    public void setsDestino(String sDestino) {
        this.sDestino = sDestino;
    }

    public Double getnPercentualGlp() {
        return nPercentualGlp;
    }

    public Product nPercentualGlp(Double nPercentualGlp) {
        this.nPercentualGlp = nPercentualGlp;
        return this;
    }

    public void setnPercentualGlp(Double nPercentualGlp) {
        this.nPercentualGlp = nPercentualGlp;
    }

    public Double getnLocField1() {
        return nLocField1;
    }

    public Product nLocField1(Double nLocField1) {
        this.nLocField1 = nLocField1;
        return this;
    }

    public void setnLocField1(Double nLocField1) {
        this.nLocField1 = nLocField1;
    }

    public Double getnLocField2() {
        return nLocField2;
    }

    public Product nLocField2(Double nLocField2) {
        this.nLocField2 = nLocField2;
        return this;
    }

    public void setnLocField2(Double nLocField2) {
        this.nLocField2 = nLocField2;
    }

    public Double getnLocField3() {
        return nLocField3;
    }

    public Product nLocField3(Double nLocField3) {
        this.nLocField3 = nLocField3;
        return this;
    }

    public void setnLocField3(Double nLocField3) {
        this.nLocField3 = nLocField3;
    }

    public Double getnLocField4() {
        return nLocField4;
    }

    public Product nLocField4(Double nLocField4) {
        this.nLocField4 = nLocField4;
        return this;
    }

    public void setnLocField4(Double nLocField4) {
        this.nLocField4 = nLocField4;
    }

    public Double getnLocField5() {
        return nLocField5;
    }

    public Product nLocField5(Double nLocField5) {
        this.nLocField5 = nLocField5;
        return this;
    }

    public void setnLocField5(Double nLocField5) {
        this.nLocField5 = nLocField5;
    }

    public Double getnLocField6() {
        return nLocField6;
    }

    public Product nLocField6(Double nLocField6) {
        this.nLocField6 = nLocField6;
        return this;
    }

    public void setnLocField6(Double nLocField6) {
        this.nLocField6 = nLocField6;
    }

    public Double getnLocField7() {
        return nLocField7;
    }

    public Product nLocField7(Double nLocField7) {
        this.nLocField7 = nLocField7;
        return this;
    }

    public void setnLocField7(Double nLocField7) {
        this.nLocField7 = nLocField7;
    }

    public Double getnLocField8() {
        return nLocField8;
    }

    public Product nLocField8(Double nLocField8) {
        this.nLocField8 = nLocField8;
        return this;
    }

    public void setnLocField8(Double nLocField8) {
        this.nLocField8 = nLocField8;
    }

    public String getsLocField1() {
        return sLocField1;
    }

    public Product sLocField1(String sLocField1) {
        this.sLocField1 = sLocField1;
        return this;
    }

    public void setsLocField1(String sLocField1) {
        this.sLocField1 = sLocField1;
    }

    public String getsLocField2() {
        return sLocField2;
    }

    public Product sLocField2(String sLocField2) {
        this.sLocField2 = sLocField2;
        return this;
    }

    public void setsLocField2(String sLocField2) {
        this.sLocField2 = sLocField2;
    }

    public String getsLocField3() {
        return sLocField3;
    }

    public Product sLocField3(String sLocField3) {
        this.sLocField3 = sLocField3;
        return this;
    }

    public void setsLocField3(String sLocField3) {
        this.sLocField3 = sLocField3;
    }

    public String getsLocField4() {
        return sLocField4;
    }

    public Product sLocField4(String sLocField4) {
        this.sLocField4 = sLocField4;
        return this;
    }

    public void setsLocField4(String sLocField4) {
        this.sLocField4 = sLocField4;
    }

    public String getsLocField5() {
        return sLocField5;
    }

    public Product sLocField5(String sLocField5) {
        this.sLocField5 = sLocField5;
        return this;
    }

    public void setsLocField5(String sLocField5) {
        this.sLocField5 = sLocField5;
    }

    public Double getnIdDocOcr() {
        return nIdDocOcr;
    }

    public Product nIdDocOcr(Double nIdDocOcr) {
        this.nIdDocOcr = nIdDocOcr;
        return this;
    }

    public void setnIdDocOcr(Double nIdDocOcr) {
        this.nIdDocOcr = nIdDocOcr;
    }

    public String getsLocField6() {
        return sLocField6;
    }

    public Product sLocField6(String sLocField6) {
        this.sLocField6 = sLocField6;
        return this;
    }

    public void setsLocField6(String sLocField6) {
        this.sLocField6 = sLocField6;
    }

    public String getsLocField7() {
        return sLocField7;
    }

    public Product sLocField7(String sLocField7) {
        this.sLocField7 = sLocField7;
        return this;
    }

    public void setsLocField7(String sLocField7) {
        this.sLocField7 = sLocField7;
    }

    public String getsLocField8() {
        return sLocField8;
    }

    public Product sLocField8(String sLocField8) {
        this.sLocField8 = sLocField8;
        return this;
    }

    public void setsLocField8(String sLocField8) {
        this.sLocField8 = sLocField8;
    }

    public String getsLocField9() {
        return sLocField9;
    }

    public Product sLocField9(String sLocField9) {
        this.sLocField9 = sLocField9;
        return this;
    }

    public void setsLocField9(String sLocField9) {
        this.sLocField9 = sLocField9;
    }

    public String getsLocField10() {
        return sLocField10;
    }

    public Product sLocField10(String sLocField10) {
        this.sLocField10 = sLocField10;
        return this;
    }

    public void setsLocField10(String sLocField10) {
        this.sLocField10 = sLocField10;
    }

    public String getsLocField11() {
        return sLocField11;
    }

    public Product sLocField11(String sLocField11) {
        this.sLocField11 = sLocField11;
        return this;
    }

    public void setsLocField11(String sLocField11) {
        this.sLocField11 = sLocField11;
    }

    public String getsLocField12() {
        return sLocField12;
    }

    public Product sLocField12(String sLocField12) {
        this.sLocField12 = sLocField12;
        return this;
    }

    public void setsLocField12(String sLocField12) {
        this.sLocField12 = sLocField12;
    }

    public String getsLocField13() {
        return sLocField13;
    }

    public Product sLocField13(String sLocField13) {
        this.sLocField13 = sLocField13;
        return this;
    }

    public void setsLocField13(String sLocField13) {
        this.sLocField13 = sLocField13;
    }

    public String getsLocField14() {
        return sLocField14;
    }

    public Product sLocField14(String sLocField14) {
        this.sLocField14 = sLocField14;
        return this;
    }

    public void setsLocField14(String sLocField14) {
        this.sLocField14 = sLocField14;
    }

    public String getsLocField15() {
        return sLocField15;
    }

    public Product sLocField15(String sLocField15) {
        this.sLocField15 = sLocField15;
        return this;
    }

    public void setsLocField15(String sLocField15) {
        this.sLocField15 = sLocField15;
    }

    public String getsCodProdAnvisa() {
        return sCodProdAnvisa;
    }

    public Product sCodProdAnvisa(String sCodProdAnvisa) {
        this.sCodProdAnvisa = sCodProdAnvisa;
        return this;
    }

    public void setsCodProdAnvisa(String sCodProdAnvisa) {
        this.sCodProdAnvisa = sCodProdAnvisa;
    }

    public String getsDescProdAnp() {
        return sDescProdAnp;
    }

    public Product sDescProdAnp(String sDescProdAnp) {
        this.sDescProdAnp = sDescProdAnp;
        return this;
    }

    public void setsDescProdAnp(String sDescProdAnp) {
        this.sDescProdAnp = sDescProdAnp;
    }

    public Double getnPercGlpNac() {
        return nPercGlpNac;
    }

    public Product nPercGlpNac(Double nPercGlpNac) {
        this.nPercGlpNac = nPercGlpNac;
        return this;
    }

    public void setnPercGlpNac(Double nPercGlpNac) {
        this.nPercGlpNac = nPercGlpNac;
    }

    public Double getnPercGlpImp() {
        return nPercGlpImp;
    }

    public Product nPercGlpImp(Double nPercGlpImp) {
        this.nPercGlpImp = nPercGlpImp;
        return this;
    }

    public void setnPercGlpImp(Double nPercGlpImp) {
        this.nPercGlpImp = nPercGlpImp;
    }

    public Double getnValorPartida() {
        return nValorPartida;
    }

    public Product nValorPartida(Double nValorPartida) {
        this.nValorPartida = nValorPartida;
        return this;
    }

    public void setnValorPartida(Double nValorPartida) {
        this.nValorPartida = nValorPartida;
    }

    public String getsGtinUnidTrib() {
        return sGtinUnidTrib;
    }

    public Product sGtinUnidTrib(String sGtinUnidTrib) {
        this.sGtinUnidTrib = sGtinUnidTrib;
        return this;
    }

    public void setsGtinUnidTrib(String sGtinUnidTrib) {
        this.sGtinUnidTrib = sGtinUnidTrib;
    }

    public String getsCodigoModalidade() {
        return sCodigoModalidade;
    }

    public Product sCodigoModalidade(String sCodigoModalidade) {
        this.sCodigoModalidade = sCodigoModalidade;
        return this;
    }

    public void setsCodigoModalidade(String sCodigoModalidade) {
        this.sCodigoModalidade = sCodigoModalidade;
    }

    public String getsCodigogpc() {
        return sCodigogpc;
    }

    public Product sCodigogpc(String sCodigogpc) {
        this.sCodigogpc = sCodigogpc;
        return this;
    }

    public void setsCodigogpc(String sCodigogpc) {
        this.sCodigogpc = sCodigogpc;
    }

    public String getsCodigogpcbrick() {
        return sCodigogpcbrick;
    }

    public Product sCodigogpcbrick(String sCodigogpcbrick) {
        this.sCodigogpcbrick = sCodigogpcbrick;
        return this;
    }

    public void setsCodigogpcbrick(String sCodigogpcbrick) {
        this.sCodigogpcbrick = sCodigogpcbrick;
    }

    public String getsCodigounspsc() {
        return sCodigounspsc;
    }

    public Product sCodigounspsc(String sCodigounspsc) {
        this.sCodigounspsc = sCodigounspsc;
        return this;
    }

    public void setsCodigounspsc(String sCodigounspsc) {
        this.sCodigounspsc = sCodigounspsc;
    }

    public String getsSituacao() {
        return sSituacao;
    }

    public Product sSituacao(String sSituacao) {
        this.sSituacao = sSituacao;
        return this;
    }

    public void setsSituacao(String sSituacao) {
        this.sSituacao = sSituacao;
    }

    public String getsEnviado() {
        return sEnviado;
    }

    public Product sEnviado(String sEnviado) {
        this.sEnviado = sEnviado;
        return this;
    }

    public void setsEnviado(String sEnviado) {
        this.sEnviado = sEnviado;
    }

    public String getsMotivoIsencaoAnvisa() {
        return sMotivoIsencaoAnvisa;
    }

    public Product sMotivoIsencaoAnvisa(String sMotivoIsencaoAnvisa) {
        this.sMotivoIsencaoAnvisa = sMotivoIsencaoAnvisa;
        return this;
    }

    public void setsMotivoIsencaoAnvisa(String sMotivoIsencaoAnvisa) {
        this.sMotivoIsencaoAnvisa = sMotivoIsencaoAnvisa;
    }

    public String getsIcProntoParaEnvio() {
        return sIcProntoParaEnvio;
    }

    public Product sIcProntoParaEnvio(String sIcProntoParaEnvio) {
        this.sIcProntoParaEnvio = sIcProntoParaEnvio;
        return this;
    }

    public void setsIcProntoParaEnvio(String sIcProntoParaEnvio) {
        this.sIcProntoParaEnvio = sIcProntoParaEnvio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", idProduto=" + getIdProduto() +
            ", codRecipiente='" + getCodRecipiente() + "'" +
            ", partNumber='" + getPartNumber() + "'" +
            ", tpEmbalagem='" + getTpEmbalagem() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", idDispositivo=" + getIdDispositivo() +
            ", unidadeEstoque='" + getUnidadeEstoque() + "'" +
            ", naladincca='" + getNaladincca() + "'" +
            ", ncm='" + getNcm() + "'" +
            ", naladish='" + getNaladish() + "'" +
            ", linhaProduto='" + getLinhaProduto() + "'" +
            ", pesoBruto=" + getPesoBruto() +
            ", pesoLiquido=" + getPesoLiquido() +
            ", registroMs='" + getRegistroMs() + "'" +
            ", validade='" + getValidade() + "'" +
            ", necessitaLi='" + getNecessitaLi() + "'" +
            ", recof='" + getRecof() + "'" +
            ", reducaoIcms=" + getReducaoIcms() +
            ", codOnu='" + getCodOnu() + "'" +
            ", seqSuframa='" + getSeqSuframa() + "'" +
            ", naoTributavel='" + getNaoTributavel() + "'" +
            ", ipiEspecifico='" + getIpiEspecifico() + "'" +
            ", iiEspecifico='" + getIiEspecifico() + "'" +
            ", ii=" + getIi() +
            ", ipi=" + getIpi() +
            ", valorUnitaria=" + getValorUnitaria() +
            ", capacidadeUnitaria=" + getCapacidadeUnitaria() +
            ", fatorConversao=" + getFatorConversao() +
            ", descricaoResumida='" + getDescricaoResumida() + "'" +
            ", atualizacao='" + getAtualizacao() + "'" +
            ", status='" + getStatus() + "'" +
            ", unidadePeso='" + getUnidadePeso() + "'" +
            ", codUnidadeQtde='" + getCodUnidadeQtde() + "'" +
            ", codUnidadeComercializada='" + getCodUnidadeComercializada() + "'" +
            ", codUnidadeUnitaria='" + getCodUnidadeUnitaria() + "'" +
            ", pesoQuilo=" + getPesoQuilo() +
            ", pesoUnidComercializada=" + getPesoUnidComercializada() +
            ", codExternoGip='" + getCodExternoGip() + "'" +
            ", ultimoInformante=" + getUltimoInformante() +
            ", tsp='" + getTsp() + "'" +
            ", tipoRecof='" + getTipoRecof() + "'" +
            ", obs='" + getObs() + "'" +
            ", pesoRateavel='" + getPesoRateavel() + "'" +
            ", necessitaRevisao='" + getNecessitaRevisao() + "'" +
            ", tipoProduto='" + getTipoProduto() + "'" +
            ", procedencia='" + getProcedencia() + "'" +
            ", chassi='" + getChassi() + "'" +
            ", especificacaoTecnica='" + getEspecificacaoTecnica() + "'" +
            ", materiaPrimaBasica='" + getMateriaPrimaBasica() + "'" +
            ", automatico='" + getAutomatico() + "'" +
            ", codOrigem='" + getCodOrigem() + "'" +
            ", materialGenerico='" + getMaterialGenerico() + "'" +
            ", cargaPerigosa='" + getCargaPerigosa() + "'" +
            ", codUnidadeVenda='" + getCodUnidadeVenda() + "'" +
            ", flexField1='" + getFlexField1() + "'" +
            ", flexField2='" + getFlexField2() + "'" +
            ", flexField3='" + getFlexField3() + "'" +
            ", descricaoDetalhada='" + getDescricaoDetalhada() + "'" +
            ", idOrganizacao=" + getIdOrganizacao() +
            ", codPaisOrigem='" + getCodPaisOrigem() + "'" +
            ", cicloProdutivo=" + getCicloProdutivo() +
            ", partNumberFornecedor='" + getPartNumberFornecedor() + "'" +
            ", flagAtualizaIcms='" + getFlagAtualizaIcms() + "'" +
            ", idDispositivoIpi=" + getIdDispositivoIpi() +
            ", codigoMoeda='" + getCodigoMoeda() + "'" +
            ", valorUnitario=" + getValorUnitario() +
            ", codProd='" + getCodProd() + "'" +
            ", codProducao='" + getCodProducao() + "'" +
            ", procedenciaExp='" + getProcedenciaExp() + "'" +
            ", idAnuencia=" + getIdAnuencia() +
            ", pesoMetroCubico=" + getPesoMetroCubico() +
            ", hts='" + getHts() + "'" +
            ", nomeComercial='" + getNomeComercial() + "'" +
            ", idModelo=" + getIdModelo() +
            ", unidadeFracionada='" + getUnidadeFracionada() + "'" +
            ", difPesoEmb=" + getDifPesoEmb() +
            ", classProdRecof='" + getClassProdRecof() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", dataInsertMov='" + getDataInsertMov() + "'" +
            ", idCorporativo='" + getIdCorporativo() + "'" +
            ", dataGerLeg='" + getDataGerLeg() + "'" +
            ", procedenciaInfo='" + getProcedenciaInfo() + "'" +
            ", codProdSuframa='" + getCodProdSuframa() + "'" +
            ", pxExpTipoins='" + getPxExpTipoins() + "'" +
            ", tipoProdSuframa='" + getTipoProdSuframa() + "'" +
            ", idDetalheSuframa=" + getIdDetalheSuframa() +
            ", valorUnitarioReal=" + getValorUnitarioReal() +
            ", necessitaRevisaoPexpam='" + getNecessitaRevisaoPexpam() + "'" +
            ", modelo=" + getModelo() +
            ", pxModeloPadrao=" + getPxModeloPadrao() +
            ", flexField4='" + getFlexField4() + "'" +
            ", flexField5='" + getFlexField5() + "'" +
            ", flexField6='" + getFlexField6() + "'" +
            ", flexField7='" + getFlexField7() + "'" +
            ", flexField8='" + getFlexField8() + "'" +
            ", flexField9='" + getFlexField9() + "'" +
            ", flexField10='" + getFlexField10() + "'" +
            ", flexField11='" + getFlexField11() + "'" +
            ", pisCofinsTipoAplic='" + getPisCofinsTipoAplic() + "'" +
            ", pis=" + getPis() +
            ", cofins=" + getCofins() +
            ", pisCofinsRedBase=" + getPisCofinsRedBase() +
            ", modeloProdSuframa='" + getModeloProdSuframa() + "'" +
            ", codSiscomexUnidadeNcm='" + getCodSiscomexUnidadeNcm() + "'" +
            ", partNumberCliente='" + getPartNumberCliente() + "'" +
            ", superficieUnitaria=" + getSuperficieUnitaria() +
            ", localEstoque='" + getLocalEstoque() + "'" +
            ", codUnidadeSuperficie='" + getCodUnidadeSuperficie() + "'" +
            ", rateioProdutoAcabado='" + getRateioProdutoAcabado() + "'" +
            ", pisCofinsCodUnEspec='" + getPisCofinsCodUnEspec() + "'" +
            ", recuperaImpostos='" + getRecuperaImpostos() + "'" +
            ", flagNoRaf='" + getFlagNoRaf() + "'" +
            ", notaComplTipi='" + getNotaComplTipi() + "'" +
            ", ipiReduzido=" + getIpiReduzido() +
            ", sujeitoLote='" + getSujeitoLote() + "'" +
            ", marcaComercial='" + getMarcaComercial() + "'" +
            ", tipoEmbalagem='" + getTipoEmbalagem() + "'" +
            ", numLiberacaoBrasilia='" + getNumLiberacaoBrasilia() + "'" +
            ", temperaturaConservacao='" + getTemperaturaConservacao() + "'" +
            ", umidade='" + getUmidade() + "'" +
            ", luminosidade='" + getLuminosidade() + "'" +
            ", embalagemSecundaria='" + getEmbalagemSecundaria() + "'" +
            ", formaFisica='" + getFormaFisica() + "'" +
            ", finalidade='" + getFinalidade() + "'" +
            ", itemProdutivoRc='" + getItemProdutivoRc() + "'" +
            ", embalagemPrimaria='" + getEmbalagemPrimaria() + "'" +
            ", descricaoAnvisa='" + getDescricaoAnvisa() + "'" +
            ", volume=" + getVolume() +
            ", codUnidadeMedidaDimensao='" + getCodUnidadeMedidaDimensao() + "'" +
            ", codMaterial='" + getCodMaterial() + "'" +
            ", ativo='" + getAtivo() + "'" +
            ", codigoAduana='" + getCodigoAduana() + "'" +
            ", classeRisco='" + getClasseRisco() + "'" +
            ", codRisco='" + getCodRisco() + "'" +
            ", flexField12='" + getFlexField12() + "'" +
            ", flexField13='" + getFlexField13() + "'" +
            ", flexField1Number=" + getFlexField1Number() +
            ", flexField2Number=" + getFlexField2Number() +
            ", flexField3Number=" + getFlexField3Number() +
            ", flexField4Number=" + getFlexField4Number() +
            ", flexField5Number=" + getFlexField5Number() +
            ", statusScansys='" + getStatusScansys() + "'" +
            ", codEstruturaAtual=" + getCodEstruturaAtual() +
            ", percTolerancia=" + getPercTolerancia() +
            ", pisEspecifico='" + getPisEspecifico() + "'" +
            ", cofinsEspecifico='" + getCofinsEspecifico() + "'" +
            ", flexField14='" + getFlexField14() + "'" +
            ", flexField15='" + getFlexField15() + "'" +
            ", flexField16='" + getFlexField16() + "'" +
            ", flexField17='" + getFlexField17() + "'" +
            ", flexField18='" + getFlexField18() + "'" +
            ", flexField19='" + getFlexField19() + "'" +
            ", flexField20='" + getFlexField20() + "'" +
            ", flexField21='" + getFlexField21() + "'" +
            ", flexField22='" + getFlexField22() + "'" +
            ", flexField23='" + getFlexField23() + "'" +
            ", flexField24='" + getFlexField24() + "'" +
            ", flexField25='" + getFlexField25() + "'" +
            ", flexField26='" + getFlexField26() + "'" +
            ", flexField27='" + getFlexField27() + "'" +
            ", flexField28='" + getFlexField28() + "'" +
            ", flexField29='" + getFlexField29() + "'" +
            ", flexField30='" + getFlexField30() + "'" +
            ", flexField31='" + getFlexField31() + "'" +
            ", flexField32='" + getFlexField32() + "'" +
            ", flexField33='" + getFlexField33() + "'" +
            ", sCodBarrasGtin='" + getsCodBarrasGtin() + "'" +
            ", nVlrUnitLimiteUsd=" + getnVlrUnitLimiteUsd() +
            ", nCodProdAnp=" + getnCodProdAnp() +
            ", nCustoProducao=" + getnCustoProducao() +
            ", sDestino='" + getsDestino() + "'" +
            ", nPercentualGlp=" + getnPercentualGlp() +
            ", nLocField1=" + getnLocField1() +
            ", nLocField2=" + getnLocField2() +
            ", nLocField3=" + getnLocField3() +
            ", nLocField4=" + getnLocField4() +
            ", nLocField5=" + getnLocField5() +
            ", nLocField6=" + getnLocField6() +
            ", nLocField7=" + getnLocField7() +
            ", nLocField8=" + getnLocField8() +
            ", sLocField1='" + getsLocField1() + "'" +
            ", sLocField2='" + getsLocField2() + "'" +
            ", sLocField3='" + getsLocField3() + "'" +
            ", sLocField4='" + getsLocField4() + "'" +
            ", sLocField5='" + getsLocField5() + "'" +
            ", nIdDocOcr=" + getnIdDocOcr() +
            ", sLocField6='" + getsLocField6() + "'" +
            ", sLocField7='" + getsLocField7() + "'" +
            ", sLocField8='" + getsLocField8() + "'" +
            ", sLocField9='" + getsLocField9() + "'" +
            ", sLocField10='" + getsLocField10() + "'" +
            ", sLocField11='" + getsLocField11() + "'" +
            ", sLocField12='" + getsLocField12() + "'" +
            ", sLocField13='" + getsLocField13() + "'" +
            ", sLocField14='" + getsLocField14() + "'" +
            ", sLocField15='" + getsLocField15() + "'" +
            ", sCodProdAnvisa='" + getsCodProdAnvisa() + "'" +
            ", sDescProdAnp='" + getsDescProdAnp() + "'" +
            ", nPercGlpNac=" + getnPercGlpNac() +
            ", nPercGlpImp=" + getnPercGlpImp() +
            ", nValorPartida=" + getnValorPartida() +
            ", sGtinUnidTrib='" + getsGtinUnidTrib() + "'" +
            ", sCodigoModalidade='" + getsCodigoModalidade() + "'" +
            ", sCodigogpc='" + getsCodigogpc() + "'" +
            ", sCodigogpcbrick='" + getsCodigogpcbrick() + "'" +
            ", sCodigounspsc='" + getsCodigounspsc() + "'" +
            ", sSituacao='" + getsSituacao() + "'" +
            ", sEnviado='" + getsEnviado() + "'" +
            ", sMotivoIsencaoAnvisa='" + getsMotivoIsencaoAnvisa() + "'" +
            ", sIcProntoParaEnvio='" + getsIcProntoParaEnvio() + "'" +
            "}";
    }
}
