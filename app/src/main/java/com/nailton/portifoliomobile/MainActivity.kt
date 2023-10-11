package com.nailton.portifoliomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
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
    Box(
        Modifier
            .fillMaxWidth()
            .background(brush = Brush.linearGradient(colorList))
            .paint(
                painterResource(id = R.drawable.desenho),
                contentScale = ContentScale.FillBounds
            )
            .verticalScroll(state)
    ) {
        Content(modifier = Modifier)
    }
}

@Composable
private fun Content(modifier: Modifier) {
    val constraint = myConstraintSet()
    ConstraintLayout(
        constraint,
        modifier
    ) {
        Header(modifier = Modifier)
        ContentBody(modifier = Modifier)
    }
}

@Composable
private fun Header(modifier: Modifier) {
    val constraint = myConstraintSet()
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
private fun ContentBody(modifier: Modifier) {
    val constraint = myConstraintSet()
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
            modifier.padding(12.dp)
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

private fun myConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val imagePerfil = createRefFor("imagePerfil")
        val titleText = createRefFor("titleText")
        val contentBody = createRefFor("contentBody")
        val header = createRefFor("header")
        val contentBodyTitleText = createRefFor("contentBodyTitleText")
        val contentBodyText = createRefFor("contentBodyText")

        constrain(imagePerfil) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(titleText) {
            top.linkTo(imagePerfil.bottom, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(header) {
            top.linkTo(parent.top)
        }

        constrain(contentBody) {
            top.linkTo(header.bottom, 40.dp)
        }

        constrain(contentBodyTitleText) {
            top.linkTo(parent.top, 20.dp)
            centerHorizontallyTo(parent)
        }

        constrain(contentBodyText) {
            top.linkTo(contentBodyTitleText.bottom, 20.dp)
            bottom.linkTo(parent.bottom, 20.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PortifolioMobileTheme {
        MainScreen()
    }
}