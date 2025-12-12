import React, { useContext, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Context } from "../../context/AuthContext";
import "./index.css";

function Register() {
  const { handleRegister, authenticated, loading: authLoading } = useContext(Context);
  const { register, handleSubmit, watch, formState: { errors } } = useForm();
  const navigate = useNavigate();
  const [serverError, setServerError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const senha = watch("senha");

  useEffect(() => {
    if (!authLoading && authenticated) {
      navigate("/dashboard");
    }
  }, [authenticated, authLoading, navigate]);

  const onSubmit = async (data) => {
    setServerError("");
    setIsSubmitting(true);
    try {
      await handleRegister(data.nome, data.email, data.senha);
    } catch (error) {
      console.error('Erro completo:', error);
      setServerError(
        error.response?.data?.message || error.message || "Erro ao registrar. Tente novamente."
      );
      setIsSubmitting(false);
    }
  };

  return (
    <div className="register-container">
      <div className="register-box">
        <div className="register-header">
          <h1>TreinaAI</h1>
          <p>Crie sua conta e comece a treinar</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="register-form">
          <div className="form-group">
            <label htmlFor="nome">Nome Completo</label>
            <input
              type="text"
              id="nome"
              placeholder="Seu nome"
              {...register("nome", {
                required: "Nome é obrigatório",
                minLength: {
                  value: 3,
                  message: "Nome deve ter no mínimo 3 caracteres",
                },
              })}
            />
            {errors.nome && (
              <span className="error-message">{errors.nome.message}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              placeholder="seu@email.com"
              {...register("email", {
                required: "Email é obrigatório",
                pattern: {
                  value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                  message: "Email inválido",
                },
              })}
            />
            {errors.email && (
              <span className="error-message">{errors.email.message}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="senha">Senha</label>
            <input
              type="password"
              id="senha"
              placeholder="Digite uma senha"
              {...register("senha", {
                required: "Senha é obrigatória",
                minLength: {
                  value: 6,
                  message: "Senha deve ter no mínimo 6 caracteres",
                },
              })}
            />
            {errors.senha && (
              <span className="error-message">{errors.senha.message}</span>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="confirmSenha">Confirmar Senha</label>
            <input
              type="password"
              id="confirmSenha"
              placeholder="Confirme sua senha"
              {...register("confirmSenha", {
                required: "Confirmação de senha é obrigatória",
                validate: (value) =>
                  value === senha || "As senhas não conferem",
              })}
            />
            {errors.confirmSenha && (
              <span className="error-message">{errors.confirmSenha.message}</span>
            )}
          </div>

          {serverError && (
            <div className="alert alert-error">{serverError}</div>
          )}

          <button
            type="submit"
            className="btn btn-primary"
            disabled={authLoading || isSubmitting}
          >
            {isSubmitting ? "Registrando..." : "Criar Conta"}
          </button>
        </form>

        <div className="register-footer">
          <p>
            Já tem uma conta?{" "}
            <Link to="/login" className="link">
              Faça login aqui
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Register;
