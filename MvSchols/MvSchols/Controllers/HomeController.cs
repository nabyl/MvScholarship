using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text.RegularExpressions;
using System.Web;
using System.Web.Mvc;
using HtmlAgilityPack;

namespace MvSchols.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index(int id)
        {
            //read the data from the website and add to the db
            var list = new List<object>();
            HtmlDocument page = new HtmlWeb().Load("http://www.dhe.gov.mv/?paged=" + id);
            var articles = page.DocumentNode.SelectNodes("//div[@id='content']//article");


            List<string> pageTitles = new List<string>();

            foreach (var article in articles)
            {
                var scholarship = new Scholarhip();

                //get title
                scholarship.Title = article.SelectSingleNode(".//h1//a").InnerText;

                //get url 
                //  scholarship.Links = article.SelectSingleNode(".//h1//a").Attributes[0].Value;


                //get documents
                var documents = article.SelectNodes(".//div[@class='entry-content']//p//a");

                foreach (HtmlNode document in documents)
                {
                    var documentLink = new Document();

                    documentLink.Name = document.InnerText;
                    documentLink.Link = document.Attributes[0].Value;

                    scholarship.Links.Add(documentLink);
                }

                list.Add(new { Scholarship = scholarship });
            }

            return Json(list, JsonRequestBehavior.AllowGet);


        }


        public ActionResult Scholarships(int id = 1)
        {
            //read the data from the website and add to the db
            var list = new List<object>();
            HtmlDocument page = new HtmlWeb().Load("http://www.dhe.gov.mv/?paged=" + id);
            var articles = page.DocumentNode.SelectNodes("//div[@id='content']//article");


            List<string> pageTitles = new List<string>();

            foreach (var article in articles)
            {
                var scholarship = new Scholarhip();

                //get title
                scholarship.Title = article.SelectSingleNode(".//h1//a").InnerText;

                //get url 
              //  scholarship.Links = article.SelectSingleNode(".//h1//a").Attributes[0].Value;


                //get documents
                var documents = article.SelectNodes(".//div[@class='entry-content']//p//a");

                foreach (HtmlNode document in documents)
                {
                    var documentLink = new Document();

                    documentLink.Name = document.InnerText;
                    documentLink.Link = document.Attributes[0].Value;

                    scholarship.Links.Add(documentLink);
                }

                list.Add(new { Scholarship = scholarship });
            }

            return Json(list, JsonRequestBehavior.AllowGet);



        }

        public class Scholarhip
        {
            public string Title { get; set; }
            public List<Document> Links = new List<Document>();
        }

        public class Document
        {
            public string Name { get; set; }
            public string Link { get; set; }
        }
    }
}
