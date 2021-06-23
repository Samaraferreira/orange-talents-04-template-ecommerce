package br.com.zup.edu.ecommerce.utils.email;

import br.com.zup.edu.ecommerce.product.question.ProductQuestion;
import br.com.zup.edu.ecommerce.purchase.Purchase;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Emails {

    @Autowired
    private Mailer mailer;

    public void newQuestion(@NotNull @Valid ProductQuestion question) {
        mailer.send("novapergunta@nossomercadolivre.com", question.getProductOwner().getLogin(),
                question.getAuthor().getLogin(), "Nova compra...", "<html>...</html>");
    }

    public void newPurchase(@NotNull @Valid Purchase purchase) {
        mailer.send("novapergunta@nossomercadolivre.com", purchase.getProductOwner().getLogin(),
                purchase.getPurchaser().getLogin(), "Nova pergunta...", "<html>...</html>");
    }

}