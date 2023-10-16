package com.nailton.portifoliomobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.nailton.portifoliomobile.ui.theme.PortifolioMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortifolioMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val colorList = listOf(Color(0xFF421fdf), Color(0xFF0c22de))
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100)}
    val constraint = myConstraintSet()

    ConstraintLayout(
        constraint
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colorList))
                .paint(
                    painterResource(id = R.drawable.desenho),
                    contentScale = ContentScale.FillBounds
                ),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(650.dp)
                    .verticalScroll(state)
                    .layoutId("scrollId"),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Content(constraint = constraint, modifier = Modifier)
            }
            ContactBottom(constraint, modifier = Modifier)
        }
    }
}

@Composable
private fun Content(constraint: ConstraintSet, modifier: Modifier) {
    Header(constraint, modifier)
    ContentBody(constraint, modifier)
}

@Composable
private fun Header(constraint: ConstraintSet, modifier: Modifier) {
    ConstraintLayout(
        constraint,
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .layoutId("header")
    ) {
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = stringResource(id = R.string.perfil),
            modifier
                .width(140.dp)
                .height(140.dp)
                .clip(CircleShape)
                .layoutId("imagePerfil")
        )

        TitleText(text = "Nailton Jr.", modifier.layoutId("titleText"))
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun ContentBody(constraint: ConstraintSet, modifier: Modifier) {
    ConstraintLayout(
        constraint,
        modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .fillMaxWidth()
            .layoutId("contentBody")
    ) {
        TitleText(text = "Sobre Mim", modifier = Modifier.layoutId("contentBodyTitleText"))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = ParagraphStyle(
                        textAlign = TextAlign.Justify,
                        textMotion = TextMotion.Animated,
                        textDirection = TextDirection.Content
                    )
                ) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color(0xFFa5bac2),
                        )
                    ) {
                        append("Olá seja bem vindo!, me chamo Nailton tenho 24 anos e sou desenvolvedor,\n" +
                                "gosto muito de jogos - principalmente rpg - e sou apaixonado por programação e hacking, " +
                                "tenho muito interesse em desenvolver minhas habilidades ao máximo e no processo aprender coisas novas, " +
                                "em meus projetos uso diferentes linguagens de programação, gosto muito de programar para mobile com kotlin\n" +
                                "e react native, spring para backend e React para front-end Web.")
                    }
                }
            },
            modifier
                .padding(12.dp)
                .layoutId("contentBodyText")
        )
    }
}

@Composable
private fun TitleText(text: String, modifier: Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color(0xFFa5bac2),
                )
            ) {
                append(text)
            }
        },
        modifier
    )
}

@Composable
private fun ContactBottom(constraint: ConstraintSet, modifier: Modifier) {
    val context = LocalContext.current
    val sendEmail = {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_EMAIL, arrayOf("nailton_junior@protonmail"))
            putExtra(Intent.EXTRA_SUBJECT, "Ola vim pelo seu portifolio")
            type = "text/plain"
        }, null)
        context.startActivity(share)
    }

    val clickGit = {
        context.startActivity(
            Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse("https://github.com/Naillton")
            }
        )
    }

    val clickLinkedin = {
        context.startActivity(
            Intent(Intent.ACTION_VIEW).also {
                it.data = Uri.parse("https://www.linkedin.com/in/nailtonjr/")
            }
        )
    }

    ConstraintLayout(
        constraint,
    ) {
        Box(
            Modifier.layoutId("contentContact")
        ) {
            Row(
                Modifier.fillMaxWidth()
                    .height(80.dp)
                    .border(
                        border = BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageButton(
                    id = R.drawable.baseline_computer_24,
                    desc = "githubButton",
                    btnName = "GitHub",
                    clickGit,
                    constraint
                )

                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                )

                ImageButton(
                    id = R.drawable.baseline_person_24,
                    desc = "linkedinButton",
                    btnName = "Linkedin",
                    clickLinkedin,
                    constraint
                )

                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                )

                ImageButton(
                    id = R.drawable.baseline_sms_24,
                    desc = "emailbButton",
                    btnName = "Email",
                    sendEmail,
                    constraint
                )
            }
        }
    }
}

@Composable
private fun ImageButton(
    id: Int,
    desc: String,
    btnName: String,
    onClick: () -> Unit,
    constraint: ConstraintSet) {
    ConstraintLayout(
        constraint
    ) {
        Button(
            onClick = { onClick() },
            Modifier
                .padding(horizontal = 14.dp)
                .layoutId("contentButonContact"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
                ),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = id),
                    contentDescription = desc,
                    Modifier.size(30.dp, 30.dp)
                )
                Text(
                    text = btnName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Cyan
                )
            }
        }
    }
}

private fun myConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val imagePerfil = createRefFor("imagePerfil")
        val titleText = createRefFor("titleText")
        val contentBody = createRefFor("contentBody")
        val header = createRefFor("header")
        val contentBodyTitleText = createRefFor("contentBodyTitleText")
        val contentBodyText = createRefFor("contentBodyText")
        val contentContact = createRefFor("contentContact")
        val contentButonContact = createRefFor("contentButonContact")
        val scrollId = createRefFor("scrollId")

        constrain(scrollId) {
            top.linkTo(parent.top)
            centerHorizontallyTo(parent)
        }

        constrain(header) {
            top.linkTo(scrollId.top)
            centerHorizontallyTo(scrollId)
        }

        constrain(imagePerfil) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(titleText) {
            top.linkTo(imagePerfil.bottom, 20.dp)
            centerHorizontallyTo(scrollId)
        }

        constrain(contentBody) {
            top.linkTo(header.bottom, 40.dp)
        }

        constrain(contentBodyTitleText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(scrollId)
        }

        constrain(contentBodyText) {
            top.linkTo(contentBodyTitleText.bottom, 20.dp)
        }

        constrain(contentContact) {
            top.linkTo(scrollId.bottom, 60.dp)
            bottom.linkTo(parent.bottom)
        }

        constrain(contentButonContact) {
            top.linkTo(parent.top)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    PortifolioMobileTheme {
        MainScreen()
    }
}