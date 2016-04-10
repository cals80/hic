package com.carlossouza

import org.openqa.selenium.{By, WebElement}
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.scalatest._
import scala.collection.JavaConverters._

/**
  * Created by carlossouza on 4/10/16.
  */
class MainSpec extends FlatSpec with Matchers {

  implicit val driver = new HtmlUnitDriver()
  driver.get("http://hubblesite.org/gallery/wallpaper/")

  "Main" should "have tests" in {
    true should === (true)
  }

  it should "open a website" in {
    driver.getTitle should startWith("HubbleSite")
  }

  it should "get all picture links" in {
    val le: List[WebElement] = driver.findElements(By.cssSelector("a.icon.wallpaper")).asScala.toList
    le.foreach { element =>
      println(element.getAttribute("href"))
    }
    le.length should be >= 1
  }

  it should "save one image to output folder" in {
    driver.get("http://hubblesite.org/gallery/wallpaper/pr2006001a/")
    val link = driver.findElementById("wallpaper-1920x1200").findElement(By.tagName("a")).getAttribute("href")
    println(link)
    link should endWith("1920x1200_wallpaper/")

    driver.get(link)
    val image = driver.findElementByClassName("subpage-body").findElement(By.tagName("img")).getAttribute("src")
    println(image)
    image should include(".jpg")

    val targetFile = "/Users/carlossouza/learning2016/hic/target/imagetest.jpg"
    new java.io.File(targetFile).delete()
    Main.fileDownloader(image, targetFile)
    (new java.io.File(targetFile)).exists() === (true)
  }

  it should "get image URL" in {
    driver.get("http://hubblesite.org/gallery/wallpaper/")
    val first: WebElement = driver.findElements(By.cssSelector("a.icon.wallpaper")).asScala.toList.head
    val imageURL = Main.getImageURL(first).getOrElse("")
    println(imageURL)
    imageURL should include(".jpg")

    driver.get("http://hubblesite.org/gallery/wallpaper/")
    val third: WebElement = driver.findElements(By.cssSelector("a.icon.wallpaper")).asScala.toList(3)
    Main.getImageURL(third) shouldBe empty
  }

}
