package com.carlossouza

import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.openqa.selenium.{WebDriver, By, WebElement}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium._
import org.openqa.selenium.htmlunit._

import sys.process._
import java.net.URL
import java.io.File
import scala.collection.JavaConverters._
/**
  * Created by carlossouza on 4/10/16.
  */
object Main {
  def main(args: Array[String]) {
    System.setProperty("webdriver.chrome.driver", "/Users/carlossouza/Downloads/chromedriver")

    val driver = new HtmlUnitDriver()
    implicit val auxDriver = new HtmlUnitDriver()
    driver.get("http://hubblesite.org/gallery/wallpaper/")
    val baseFolder = "/Users/carlossouza/Desktop/wallpapers/"

    driver.findElements(By.cssSelector("a.icon.wallpaper")).asScala.toList.foreach { element =>
      getImageURL(element) match {
        case Some(imageURL) => {
          fileDownloader(imageURL, baseFolder + element.getAttribute("id") + ".jpg")
          println("DONE: " + element.getAttribute("title"))
        }
        case None => println("SKIPPING: " + element.getAttribute("title"))
      }
    }

    driver.quit()
    auxDriver.quit()
  }

  def fileDownloader(url: String, filename: String) = {
    new URL(url) #> new File(filename) !!
  }

  def getImageURL(element: WebElement)(implicit driver: WebDriver): Option[String] = {
    driver.get(element.getAttribute("href"))
    try {
      val link = driver.findElement(By.id("wallpaper-1920x1200")).findElement(By.tagName("a")).getAttribute("href")
      driver.get(link)
      val imageURL = driver.findElement(By.className("subpage-body")).findElement(By.tagName("img")).getAttribute("src")
      Some(imageURL)
    } catch {
      case e: org.openqa.selenium.NoSuchElementException => None
    }
  }

}
