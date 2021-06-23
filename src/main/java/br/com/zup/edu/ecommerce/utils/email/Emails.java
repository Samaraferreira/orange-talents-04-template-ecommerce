package br.com.zup.edu.ecommerce.utils.email;

import br.com.zup.edu.ecommerce.product.question.ProductQuestion;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Emails {

    @Autowired
    private Mailer mailer;

    public void newQuestion(@NotNull @Valid ProductQuestion question) {
        mailer.send("<html>...</html>","Nova pergunta...", question.getAuthor().getLogin(),
                "novapergunta@nossomercadolivre.com", question.getProductOwner().getLogin());
    }

}